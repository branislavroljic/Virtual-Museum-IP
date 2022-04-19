import { Time } from "@angular/common";

export class VirutalVisit{
    id : number;
    date : Date;
    startTime : Time;
    duration: number;
    price : number;
    museumId : number;
    active : boolean;

  constructor(
    id: number, 
    date: Date, 
    startTime: Time, 
    duration: number, 
    price: number, 
    museumId: number, 
    active: boolean
) {
    this.id = id
    this.date = date
    this.startTime = startTime
    this.duration = duration
    this.price = price
    this.museumId = museumId
    this.active = active
  }

}