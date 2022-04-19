import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Log } from 'src/app/model/log.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  constructor(private http : HttpClient) { }

  public getAllLogs(){
    return this.http.get<Log[]>(environment.API_URL + "/logs");
  }

  public downloadLogs() : Observable<Blob>{
    return this.http.get(environment.API_URL + "/logs/download", {
      responseType : 'blob'
    });
  }
}
