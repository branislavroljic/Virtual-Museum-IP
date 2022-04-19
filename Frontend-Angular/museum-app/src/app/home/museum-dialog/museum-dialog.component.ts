import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Museum } from 'src/app/model/museum.model';
import { RestCountry } from 'src/app/model/rest-country.model';
import { GoogleMapsComponent } from '../google-maps/google-maps.component';
import { MuseumDialogService } from '../services/museum-dialog.service';
import { MuseumService } from '../services/museum.service';

@Component({
  selector: 'app-museum-dialog',
  templateUrl: './museum-dialog.component.html',
  styleUrls: ['./museum-dialog.component.css']
})
export class MuseumDialogComponent implements OnInit {

  public museum: Museum;
  form: FormGroup = new FormGroup({});
  public restCountries = Array<RestCountry>();
  public cities = Array<any>();

  constructor(private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<MuseumDialogComponent>,
    private museumDialogService: MuseumDialogService,
    private dialog: MatDialog,
    private museumService: MuseumService,
    @Inject(MAT_DIALOG_DATA) data: any) {
    this.museum = data?.museum;
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: [this.museum?.name, Validators.required],
      address: [this.museum?.address, Validators.required],
      tel: [this.museum?.tel, Validators.required],
      geolat: [this.museum?.geolat, Validators.required],
      geolng: [this.museum?.geolng, Validators.required],
      type: [this.museum?.type, Validators.required],
      country: [null, Validators.required],
      city: [null, Validators.required],
    });

    this.museumDialogService.getRestCountriesEurope().subscribe((data: any) => {
      this.restCountries = data.map((country: any) => {
        return new RestCountry(country.name.common, country.cca2);
      });
    });
  }

  addMuseum(museum: Museum) {
    this.museumService.addMuseum(museum).subscribe((data: Museum) => {
      this.dialogRef.close(data);
    },
      error => {
        alert(error.error);
      });

  }
  editMuseum(museum: Museum) {
    museum.id = this.museum.id;
    this.museumService.editMuseum(museum).subscribe((data: any) => {
      this.dialogRef.close(museum);
    },
      error => {
        alert(error.error);
      });

  }

  close() {
    this.dialogRef.close();
  }

  countryChange(event: any) {

    if (!event.isUserInput) { //prevent previous selection change
      return;
    }
    this.cities = Array<any>();
    console.log("duzina cities: " + this.cities.length);
    console.log(event.source.value);
    this.museumDialogService.getRegionByCountry(event.source.value).subscribe((data: any) => {
      data.forEach((region: any) => {
        this.museumDialogService.getCityByRegion(region).forEach((city: any) => {
          city.forEach((c: any) => this.cities.push(c));
        }
        );
      });
    });
  }

  openMapClick() {
    const selectedCity = this.form.value.city;
    let city = this.cities.find(p => p.city === selectedCity);

    console.log(JSON.stringify(city));

    const mapDialogRef = this.dialog.open(GoogleMapsComponent, {
      width: '800px',
      height: "600px",
      data: {
        lat: Number(city.latitude),
        lng: Number(city.longitude),
        readonlyMap: false
      }
    });

    mapDialogRef.afterClosed().subscribe(result => {
      this.form.controls['geolat'].setValue(result.lat);
      this.form.controls['geolng'].setValue(result.lng);
    });
  }
}
