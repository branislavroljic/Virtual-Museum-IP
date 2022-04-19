import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { GoogleMap, MapInfoWindow, MapMarker } from '@angular/google-maps';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MuseumDialogComponent } from '../museum-dialog/museum-dialog.component';

@Component({
  selector: 'app-google-maps',
  templateUrl: './google-maps.component.html',
  styleUrls: ['./google-maps.component.css']
})
export class GoogleMapsComponent implements OnInit {
  @ViewChild(GoogleMap, { static: false }) map!: GoogleMap;
  @ViewChild(MapInfoWindow, { static: false }) info!: MapInfoWindow
  @ViewChild(MapMarker, { static: false }) mapMarker!: MapMarker;

  options: google.maps.MapOptions = {
    scrollwheel: false,
    zoomControl: false,
    gestureHandling: 'none'
  }

  cityLatLng: google.maps.LatLngLiteral;
  currentLat: any;
  currentLng: any;

  marker: any | null = null;
  infoContent: string = '';
  readonlyMap: boolean = false;

  constructor(
    private dialogRef: MatDialogRef<MuseumDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.cityLatLng = {
      lat: data.lat,
      lng: data.lng
    };

    this.readonlyMap = data.readonlyMap;
  }

  ngOnInit(): void {
    if (this.readonlyMap) {
      this.marker = {
        position: this.cityLatLng,
        options: {
          animation: google.maps.Animation.BOUNCE,
        }
      };

      this.infoContent = "Geo location:" + this.cityLatLng;
      this.info.open(this.mapMarker);
    }
  }

  click(event: any) {
    if (this.readonlyMap)
      return;

    if (!this.marker) {
      this.marker = {
        position: event.latLng,
        options: {
          animation: google.maps.Animation.BOUNCE,
        }
      };
    } else {
      this.marker.position = event.latLng;
    }

    this.infoContent = "Geo location:" + event.latLng;
    this.info.open(this.mapMarker);
    this.currentLat = event.latLng.lat();
    this.currentLng = event.latLng.lng();
  }

  save() {
    let ret = {
      lat: this.currentLat,
      lng: this.currentLng
    }

    this.dialogRef.close(ret);
  }

  close() {
    this.dialogRef.close();
  }
}
