import { Component, Inject, OnDestroy, OnInit, Optional } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/services/auth.service';
import { TokenStorageService } from 'src/app/auth/services/token-storage.service';
import { Arifact } from 'src/app/model/artifact.model';
import { VirutalVisit } from 'src/app/model/virtual-visit.model';
import { MuseumsComponent } from '../museums/museums.component';
import { MuseumService } from '../services/museum.service';

@Component({
  selector: 'app-visit',
  templateUrl: './visit.component.html',
  styleUrls: ['./visit.component.css']
})
export class VisitComponent implements OnInit, OnDestroy {

  artifacts!: Arifact[];
  private apiLoaded = false;
  private museumId!: number;
  private visitId!: number;
  private ticketNumber!: string;
  private isDialog = false;
  private timer: any;

  constructor(private activatedRoute: ActivatedRoute,
    private router: Router,
    private museumService: MuseumService,
    private domSanitizer: DomSanitizer,
    private authService: AuthService,
    private tokenService: TokenStorageService,
    @Optional() private dialogRef: MatDialogRef<VisitComponent>,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) data: any) {
    this.visitId = data?.visitId;
    this.museumId = data?.museumId;
    this.isDialog = this.museumId != null && this.visitId != null;
  }
  ngOnDestroy(): void {
    clearTimeout(this.timer);
  }

  ngOnInit(): void {
    if (!this.apiLoaded) {
      const tag = document.createElement('script');
      tag.src = 'https://www.youtube.com/iframe_api';
      document.body.appendChild(tag);
      this.apiLoaded = true;
    }
    if (!this.isDialog) //real virtual visit, not admin's preview
      this.activatedRoute.params.subscribe(params => {
        this.museumId = +params['id1'];
        this.visitId = +params['id2'];
        this.ticketNumber = params['ticketNumber'];

        this.timer = this.museumService.getRemainingTimeOfVisit(this.museumId, this.visitId).subscribe(time => {
          setTimeout(() => {
            this.endVisitClick();
          }, time);
        },
          err => alert("An error occurred while getting virtula visit!"));

        this.getArtifacts();

      });
    else this.getArtifacts();

  }

  private getArtifacts() {
    this.museumService.getArtifacts(this.museumId, this.visitId).subscribe(
      data => {
        data.forEach(artifact => {

          if (artifact.type == 'image') {
            artifact.bytes = this.domSanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,' + artifact.bytes);
          } else if (artifact.type == 'mp4') {
            artifact.bytes = this.domSanitizer.bypassSecurityTrustResourceUrl('data:video/mp4;base64,' + artifact.bytes);
          } else {
            console.log(JSON.stringify(artifact));
            let uri = (artifact.uri as string);
            artifact.uri = uri.substring(uri.indexOf("v=") + 2);
          }
        })
        this.artifacts = data;
      }
    );
  }

  public endVisitClick() {
    if (this.isDialog)
      this.dialogRef.close();
    else {
      this.authService.logout().subscribe(res => {

        this.tokenService.logout();
        this.router.navigate(['/']);
      });
    }
  }
}
