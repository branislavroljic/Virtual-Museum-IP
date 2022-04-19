import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {

  private countriesAPI = "https://countriesnow.space/api/v0.1/countries/cities";

  constructor(private http : HttpClient) { }


  public getWeatherForRandomCities(country : string){
    let cities = [];
    let retArray: any[] =[];
    this.getCitiesForCountry(country).subscribe((result : any) => {
      let shuffled = result.data.sort(() => 0.5 - Math.random());
      cities = shuffled.slice(0, 3);
      
    });
    
    console.log("iz weather service");
    for(let i in retArray){
     // console.log(JSON.stringify(this.ret[i]));
      console.log(retArray[i]);
     }
    return retArray;
  }

  public getWeather(city : string){
  
      const weatherAPI = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=faf5aa13ee038bef51339c8d22859850&units=metric`

      return   this.http.get<any>(weatherAPI);
   
  }

  
  public getCitiesForCountry(country: string) {
    return this.http.post<any>(this.countriesAPI, { "country": 'Spain' });
  }
}
