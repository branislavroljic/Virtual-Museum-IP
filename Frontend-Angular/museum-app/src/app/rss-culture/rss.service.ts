import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RssData } from '../model/rss.model';

@Injectable({
  providedIn: 'root'
})
export class RssService {

  private apiURL = `https://api.rss2json.com/v1/api.json?rss_url=${environment.RSS_URL}`;

  constructor(private http : HttpClient) { 
  }

  public getCultureNews() {
    return this.http.get<RssData>(this.apiURL);
  }
}
