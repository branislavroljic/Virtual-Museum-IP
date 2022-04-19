import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WeatherComponent } from './weather/weather.component';
import { AppMaterialModule } from '../app-material/app-material.module';



@NgModule({
  declarations: [
    WeatherComponent
  ],
  imports: [
    CommonModule,
    AppMaterialModule
  ],
  exports: [WeatherComponent]
})
export class WeatherModule { }
