import { Injectable } from '@angular/core';
import { User } from 'src/app/model/user.model';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  public logout() {
    window.localStorage.clear();
  }

  public storeJwt(jwtToken: string) {
    window.localStorage.removeItem(environment.JWT_KEY);
    window.localStorage.setItem(environment.JWT_KEY, jwtToken);
  }

  public getJwt(): string | null {
    return window.localStorage.getItem(environment.JWT_KEY);
  }

  public storeRefreshToken(token: string) {
    window.localStorage.removeItem(environment.REFRESH_TOKEN_KEY);
    window.localStorage.setItem(environment.REFRESH_TOKEN_KEY, token);
  }
  public getRefreshToken(): string | null {
    return window.localStorage.getItem(environment.REFRESH_TOKEN_KEY);
  }

  public storeUser(user: User) {
    window.localStorage.removeItem(environment.USER_KEY);
    window.localStorage.setItem(environment.USER_KEY, JSON.stringify(user));
  }

  public getUser(): User | null {
    const user = window.localStorage.getItem(environment.USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return null;
  }

}
