import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegistrationRequest } from 'src/app/model/requests/registration.request';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  refreshToken(refreshToken : string){
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
    return this.http.post(environment.API_URL + '/auth/refresh_token', 
      {refreshToken : refreshToken}, httpOptions
    );
  }

  public login(username: string, password: string){
    return this.http.post(environment.API_URL + "/auth/login", {username, password});
   }

   public register(request : RegistrationRequest){
    return this.http.post<any>(environment.API_URL + "/auth/register", request);
  }

  public logout(){
    return this.http.get(environment.API_URL + "/auth/logout");
  }
}
