import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { WeatherService } from '../weather.service';

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent implements OnInit {

  @Input() country: any | undefined;

  public dataSource = new MatTableDataSource<any>();
  public displayedColumns: string[] = ['city', 'temp', 'visibility', 'wind'];


  constructor(private weatherService : WeatherService) { }

  ngOnInit(): void {
  //   this.weatherArray = this.weatherService.getWeatherForRandomCities(this.country);
  //   this.dataSource.data = this.weatherService.getWeatherForRandomCities(this.country);
  //   console.log("evo vremenaL : " + this.weatherArray.length);
  //  for(let i in this.weatherArray){
  //   console.log(JSON.stringify(this.weatherArray[i]));
  //   console.log(this.weatherArray[i]);
  //  }

  this.weatherService.getCitiesForCountry(this.country).subscribe((result : any) => {
    let shuffled = result.data.sort(() => 0.5 - Math.random());
   
    let cities = shuffled.slice(0, 3);
    cities.forEach((city: string) => this.weatherService.getWeather(city).subscribe(data => this.dataSource.data =  this.dataSource.data.concat(data)));
  });
  }

}
