import { animate, state, style, transition, trigger } from '@angular/animations';
import { AfterViewInit, Component, OnInit, QueryList, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from 'src/app/auth/services/token-storage.service';
import { ConfirmDialogComponent } from 'src/app/confirm-dialog/confirm-dialog.component';
import { Museum } from 'src/app/model/museum.model';
import { User } from 'src/app/model/user.model';
import { VirutalVisit } from 'src/app/model/virtual-visit.model';
import { WeatherService } from 'src/app/weather/weather.service';
import { GoogleMapsComponent } from '../google-maps/google-maps.component';
import { PurchaseComponent } from '../purchase/purchase.component';
import { MuseumService } from '../services/museum.service';
import { VisitDialogComponent } from '../visit-dialog/visit-dialog.component';
import { VisitComponent } from '../visit/visit.component';

@Component({
  selector: 'app-visits',
  templateUrl: './visits.component.html',
  styleUrls: ['./visits.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class VisitsComponent implements OnInit, AfterViewInit {

  futureVisitsDisplayedColumns: string[] = ['artifacts', 'date', 'startTime', 'duration', 'price', 'action', 'delete'];
  activeVisitsDisplayedColumns: string[] = ['date', 'startTime', 'duration', 'price', 'action'];
  activeVisitsDataSource = new MatTableDataSource<VirutalVisit>();
  futureVisitsDataSource = new MatTableDataSource<VirutalVisit>();

  public errorMessage = "";
  public ticketForm: FormGroup;
  public user: User | null = null;

  @ViewChild(MatPaginator) paginator = new QueryList<MatPaginator>();

  museumId! : Number;
  museum: Museum | null = null;
  constructor(private activatedRoute: ActivatedRoute,
    private router: Router,
    private museumService: MuseumService,
    private dialog: MatDialog,
    private matSnackBar: MatSnackBar,
    private weatherService: WeatherService,
    private formBuilder: FormBuilder,
    private storageService: TokenStorageService) {

    this.ticketForm = formBuilder.group({
      ticketNumber: [null, Validators.required]
    });
    this.user = storageService.getUser();
    if (this.user?.role != 'ADMIN') {
      this.futureVisitsDisplayedColumns.splice(0, 1);
      this.futureVisitsDisplayedColumns.splice(this.futureVisitsDisplayedColumns.length - 1, 1);
    }
  }
  ngAfterViewInit(): void {
    this.futureVisitsDataSource.paginator = this.paginator.toArray()[0];
    this.activeVisitsDataSource.paginator = this.paginator.toArray()[1];
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
       this.museumId = +params['id'];
      this.museumService.getMuseumById(this.museumId).subscribe(museum => {
        this.museum = museum;

        this.museumService.getFutureAndActiveVirtualVisits(this.museumId).subscribe(data => {
          this.activeVisitsDataSource.data = data.filter(v => v.active);;
          this.futureVisitsDataSource.data = data.filter(v => !v.active);
        },
        err => {
          alert(err);
        });
      },
        err => {
          alert(err);
        });
    });
  }

  public buyTicket(visit: VirutalVisit) {

    this.dialog.open(PurchaseComponent, {
      width: '300px',
      data: {
        virtualVisit: visit
      }
    });
  }

  public startTourClick(visit: VirutalVisit) {
    let ticketNumber = this.ticketForm.value.ticketNumber;
    this.museumService.checkIfTicketExists(visit, ticketNumber).subscribe(data => {
      this.router.navigate(['/home/', visit.museumId, 'visits', visit.id, { ticketNumber: ticketNumber }]);
    },
      error => this.errorMessage = error.error);
  }

  addVisit() {
    const dialogRef = this.dialog.open(VisitDialogComponent, {
      width: "300px",
      data: {
        museumId: this.museum?.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        let data = this.futureVisitsDataSource.data;
        data.push(result);
        this.futureVisitsDataSource.data = data;
      }
    });
  }

  artifactsClick(visit: VirutalVisit) {
    this.dialog.open(VisitComponent, {
      width: "700px",
      height: "500px",
      data: {
        museumId: this.museum?.id,
        visitId: visit.id
      }
    })
  }

  deleteVisit(event: any, visit: VirutalVisit) {

    this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: "Are you sure you want to delete virtual visit?"
      }
    }).afterClosed().subscribe(result => {
      if (result) {
        this.museumService.deleteVirtualVisit(visit.museumId, visit.id).subscribe(
          res => {
            let data = this.futureVisitsDataSource.data;
            data.forEach((el, index) => { if (el.id === visit.id) data.splice(index, 1); })
            this.futureVisitsDataSource.data = data;
            this.matSnackBar.open("Deleted successfully!", undefined, {
              duration: 2000,
            });
          },
          err => {
            alert(err.error);
          }
        );
      }
    });
  }

  viewOnMap() {
    const mapDialogRef = this.dialog.open(GoogleMapsComponent, {
      width: '800px',
      height: "600px",
      data: {
        lat: this.museum?.geolat,
        lng: this.museum?.geolng,
        readonlyMap: true
      }
    });
  }

  reloadVisits(){
    this.museumService.getFutureAndActiveVirtualVisits(this.museumId).subscribe(data => {
      this.activeVisitsDataSource.data = data.filter(v => v.active);;
      this.futureVisitsDataSource.data = data.filter(v => !v.active);
    },
    err => {
      alert(err);
    });
  }
}
