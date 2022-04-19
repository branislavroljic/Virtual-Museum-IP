import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Report } from 'src/app/model/report.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http : HttpClient) { }

  public countLoggedInUsers(){
    return this.http.get<number>(environment.API_URL + "/statistics/logged_in_users");
  }

  public countRegisteredUsers(){
    return this.http.get<number>(environment.API_URL + "/statistics/registered_users");
  }

  public get24HReport(){
    return this.http.get<Report[]>(environment.API_URL + "/statistics/24h_login");
  }
}
