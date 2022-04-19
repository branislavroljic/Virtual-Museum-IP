import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { MediaObserver } from '@angular/flex-layout';
import { MatDrawerMode, MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/services/auth.service';
import { TokenStorageService } from 'src/app/auth/services/token-storage.service';
import { User } from 'src/app/model/user.model';
import { environment } from 'src/environments/environment';
import { AdminAppService } from '../services/admin-app.service';

@Component({
  selector: 'app-home-container',
  templateUrl: './home-container.component.html',
  styleUrls: ['./home-container.component.css']
})
export class HomeContainerComponent implements OnInit {

  @ViewChild('sideNav', { read: MatSidenav }) sideNav: MatSidenav | null = null;

  navMode = 'side' as MatDrawerMode;

  public user: User | null = null;

  constructor(private observableMedia: MediaObserver,
    private tokenService: TokenStorageService,
    private adminAppService: AdminAppService,
    private router: Router,
    private authService: AuthService) {
    this.user = tokenService.getUser();
  }

  ngOnInit(): void {
    if (this.observableMedia.isActive('xs') || this.observableMedia.isActive('sm')) {
      this.navMode = 'over';
    }

    this.observableMedia.media$
      .subscribe(change => {
        switch (change.mqAlias) {
          case 'xs':
          case 'sm':
            this.navMode = 'over';
            this.sideNav?.close();
            break;
          default:
            this.navMode = 'side';
            this.sideNav?.open();
            break;
        }
      });
  }

  logoutClick() {
    this.authService.logout().subscribe(res => {

      this.tokenService.logout();
      this.router.navigate(['/']);
    });
  }

  adminAppClick() {
    this.adminAppService.getOtp().subscribe(
      data => {
        window.open(environment.ADMIN_APP_URL + data, '_blank');
      },
      err => {
        console.log(JSON.stringify(err));
        alert(err.error);
      }
    );
  }
}
