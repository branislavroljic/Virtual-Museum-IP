import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { MuseumsComponent } from './museums/museums.component';
import { AppMaterialModule } from '../app-material/app-material.module';
import { VisitsComponent } from './visits/visits.component';
import { PurchaseComponent } from './purchase/purchase.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WeatherModule } from '../weather/weather.module';
import { RssCultureModule } from '../rss-culture/rss-culture.module';
import { VisitComponent } from './visit/visit.component';
import { YouTubePlayerModule } from "@angular/youtube-player";
import { HomeContainerComponent } from './home-container/home-container.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { NgChartsModule } from 'ng2-charts';
import { MuseumDialogComponent } from './museum-dialog/museum-dialog.component';
import { HttpClientJsonpModule } from '@angular/common/http';
import { GoogleMapsModule } from '@angular/google-maps';
import { GoogleMapsComponent } from './google-maps/google-maps.component';
import { VisitDialogComponent } from './visit-dialog/visit-dialog.component';
import { LogsComponent } from './logs/logs.component';


@NgModule({
  declarations: [
    MuseumsComponent,
    VisitsComponent,
    PurchaseComponent,
    VisitComponent,
    HomeContainerComponent,
    HomeAdminComponent,
    MuseumDialogComponent,
    GoogleMapsComponent,
    VisitDialogComponent,
    LogsComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    AppMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    WeatherModule,
    RssCultureModule,
    YouTubePlayerModule,
    NgChartsModule,
    GoogleMapsModule
  ],
  entryComponents: [
    PurchaseComponent
  ]
})
export class HomeModule { }
