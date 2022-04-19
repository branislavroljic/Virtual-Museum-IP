import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminAppService {

  constructor(private http : HttpClient) { }

  public getOtp(){
    return this.http.get(environment.API_URL + "/auth/token", {
      responseType: "text"
   });
  }
}
