import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Arifact } from 'src/app/model/artifact.model';
import { Museum } from 'src/app/model/museum.model';
import { PurchaseRequest } from 'src/app/model/requests/purchase.request';
import { VirutalVisit } from 'src/app/model/virtual-visit.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MuseumService {

  constructor(private http : HttpClient) { }


  public addMuseum(museum : Museum){
    return this.http.post<Museum>(environment.API_URL + "/museums", museum);
  }

  public editMuseum(museum : Museum){
    return this.http.put<any>(environment.API_URL + "/museums/" + museum.id, museum);
  }

  public deleteMuseum(museum : Museum){
    return this.http.delete(environment.API_URL + "/museums/" + museum.id);
  }

  public getAllMuseums(){
    return this.http.get<Museum[]>(environment.API_URL + "/museums");
  }

  public getMuseumById(id : Number){
    const url = environment.API_URL + "/museums/" + id; 
    return this.http.get<Museum>(url);
  }

  public getRemainingTimeOfVisit(museumId : number, visitId : number){
    return this.http.get<number>(environment.API_URL + "/visits/" + visitId + "/remaining_time");
  }

  public getFutureAndActiveVirtualVisits(id: Number){
    const url = environment.API_URL + "/museums/" + id + "/visits";
    return this.http.get<VirutalVisit[]>(url);
  }

  public purchaseTicket(purchaseRequest : PurchaseRequest, virtualVisit : VirutalVisit){
    purchaseRequest.requestedAmount = virtualVisit.price;
    return this.http.post<any>(environment.API_URL + "/museums/" + virtualVisit.museumId + "/visits/" + virtualVisit.id, purchaseRequest);
  }

  public checkIfTicketExists(virtualVisit : VirutalVisit, ticketNumber : string){
    let request = {
      ticketNumber : ticketNumber
    }
    return this.http.post<any>(environment.API_URL + "/visits/" + virtualVisit.id + "/tickets", request);
  }

  public getArtifacts(museumId : number, visitId : number){
    return this.http.get<Arifact[]>(environment.API_URL + "/visits/" + visitId + "/artifacts");
  }

  public addVirtualVisit(museumId: number, formData : FormData){
    return this.http.post<VirutalVisit>(environment.API_URL + "/museums/" + museumId + "/visits/", formData);
  }

  public deleteVirtualVisit(museumId  : number, visitId : number){
    return this.http.delete(environment.API_URL + "/visits/" + visitId);
  }

}
