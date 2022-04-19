import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RssComponent } from './rss/rss.component';
import { AppMaterialModule } from '../app-material/app-material.module';



@NgModule({
  declarations: [
    RssComponent
  ],
  imports: [
    CommonModule,
    AppMaterialModule
  ],
  exports : [RssComponent]
})
export class RssCultureModule { }
