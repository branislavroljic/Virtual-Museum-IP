import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/auth/services/token-storage.service';
import { ConfirmDialogComponent } from 'src/app/confirm-dialog/confirm-dialog.component';
import { Museum } from 'src/app/model/museum.model';
import { User } from 'src/app/model/user.model';
import { MuseumDialogComponent } from '../museum-dialog/museum-dialog.component';
import { MuseumDialogService } from '../services/museum-dialog.service';
import { MuseumService } from '../services/museum.service';

@Component({
  selector: 'app-museums',
  templateUrl: './museums.component.html',
  styleUrls: ['./museums.component.css']
})
export class MuseumsComponent implements AfterViewInit {

  displayedColumns: string[] = ['edit', 'name', 'type', 'address', 'city', 'country', 'tel', 'delete'];
  public dataSource = new MatTableDataSource<Museum>();

  public user: User | null = null;

  @ViewChild(MatPaginator) paginator: MatPaginator | null = null;
  @ViewChild(MatSort) sort: MatSort | null = null;

  constructor(private museumService: MuseumService,
    private router: Router,
    private storageService: TokenStorageService,
    private dialog: MatDialog,
    private matSnackBar: MatSnackBar) {

    this.museumService.getAllMuseums().subscribe((data: Museum[]) => { this.dataSource.data = data;});

    
    this.user = storageService.getUser();
    if (this.user?.role !== 'ADMIN') {
      this.displayedColumns.splice(0, 1);
      this.displayedColumns.splice(this.displayedColumns.length - 1, 1);
    }
  }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  public museumClick(museum: any) {
    

    this.router.navigate(['/home/', museum.id, 'visits']);
  }

  editMuseum(event: any, museum: Museum) {
    event.stopPropagation();
    const dialogRef = this.dialog.open(MuseumDialogComponent, {
      width: '300px',
      data: {
        museum: museum
      }
    });

    dialogRef.afterClosed().subscribe(museum => {
      if (museum) {
        let data = this.dataSource.data;
        data.forEach((m, index)=> {
          if(m.id === museum.id){
            console.log("nasaooo" + JSON.stringify(m));
            data.splice(index, 1, museum);
          }
        });
        this.dataSource.data = data;
        this.matSnackBar.open("Museum edited successfully!", undefined, {
          duration: 2000,
        });
      }
    });    
  }

  addMuseum() {
    const dialogRef = this.dialog.open(MuseumDialogComponent, {
      width: '300px'
    });

    dialogRef.afterClosed().subscribe(museum => {
      if (museum) {
        let data = this.dataSource.data;
        data.push(museum);
        this.dataSource.data = data;
        this.matSnackBar.open("Museum added successfully!", undefined, {
          duration: 2000,
        });
      }
    });
  }

  deleteMuseum(event: any, museum: Museum) {

    event.stopPropagation();

    this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: "Are you sure you want to delete museum: " + museum.name + "?"
      }
    }).afterClosed().subscribe(result => {
      if (result) {
        this.museumService.deleteMuseum(museum).subscribe(res => {
          let data = this.dataSource.data;
          data.forEach((el, index) => { if (el.id === museum.id) data.splice(index, 1); })
          this.dataSource.data = data;
          this.matSnackBar.open("Deleted successfully!", undefined, {
            duration: 2000,
          });
        },
          error => {
            alert(error.error);
          });
      }
    });



  }
}
