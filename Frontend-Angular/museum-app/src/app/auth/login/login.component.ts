import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { TokenStorageService } from '../services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginForm: FormGroup;
  public hide = true;
  public errorMessage!: string;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router) {
    this.loginForm = formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.tokenStorage.getJwt() && this.tokenStorage.getUser()) {
      this.router.navigate(['home']);
    }
  }

  public loginClick() {
    this.authService.login(this.loginForm.value.username, this.loginForm.value.password).subscribe((data: any) => {
      this.tokenStorage.storeJwt(data.jwtToken);
      this.tokenStorage.storeRefreshToken(data.refreshToken);
      this.tokenStorage.storeUser(data);
      if (data.role === "ADMIN")
        this.router.navigate(['home/chart']);
      else
        this.router.navigate(['home']);
    },
      error => {
        this.errorMessage = error.error;
      });
  }
}
