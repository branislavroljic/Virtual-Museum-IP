import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GoogleMapsComponent } from './google-maps/google-maps.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { HomeContainerComponent } from './home-container/home-container.component';
import { LogsComponent } from './logs/logs.component';
import { MuseumsComponent } from './museums/museums.component';
import { VisitComponent } from './visit/visit.component';
import { VisitsComponent } from './visits/visits.component';

const routes: Routes = [
  {
    path: '',
    component: HomeContainerComponent,
    children: [
      {
        path: '',
        component: MuseumsComponent,
        pathMatch: 'full'
      },
      {
        path: 'chart',
        component: HomeAdminComponent
      },
      {
        path: ':id/visits',
        component: VisitsComponent
      },
      {
        path: ':id1/visits/:id2',
        component: VisitComponent
      },
      {
        path : 'logs',
        component : LogsComponent
      }
    ]
  }];

@NgModule({
      imports: [RouterModule.forChild(routes)],
      exports: [RouterModule]
    })
export class HomeRoutingModule { }
