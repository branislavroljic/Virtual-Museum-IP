import { HttpClient, HttpClientJsonpModule } from '@angular/common/http';
import { Injectable } from '@angular/core';

const BATTUTA_KEY = "foo"; // validan = "5e8681c7439d1ead10e0273258856c16";//"75a48d18199165f55a754450eabf5883";//"4da35966ab0f0e805dd8350f06f09678"; //"75a48d18199165f55a754450eabf5883";//"577f7c1d1c4927c948cf8fd4670a9358" ; //"bdf0f986bc82d85cc699f1e57f8d42c8";";

@Injectable({
  providedIn: 'root'
})
export class MuseumDialogService {

   

  constructor(private http : HttpClient) { }

  public getRestCountriesEurope(){
   return  this.http.get("https://restcountries.com/v3.1/region/europe");
  }

  public getRegionByCountry(cca2 : string){
    return this.http.jsonp("http://battuta.medunes.net/api/region/" + cca2 + "/all/?key=" + BATTUTA_KEY, 'callback');
  }

  public getCityByRegion(region : any){
    return this.http.jsonp("http://battuta.medunes.net/api/city/" + region.country +"/search/?region=" + region.region + "&key=" + BATTUTA_KEY, 'callback');
  }
}
