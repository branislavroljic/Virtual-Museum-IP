import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PurchaseRequest } from 'src/app/model/requests/purchase.request';
import { MuseumService } from '../services/museum.service';
import { VirutalVisit } from 'src/app/model/virtual-visit.model';

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.css']
})
export class PurchaseComponent implements OnInit {

  form: FormGroup = new FormGroup({});;//forma
  hide = true;
  buyTicketClicked = false;
  //@Inject(MAT_DIALOG_DATA) public data : any;

  private virtualVisit : VirutalVisit;
  public cardTypes = ['VISA', 'MASTERCARD', 'AMERICAN_EXPRESS'];

  constructor(
    private formBuilder : FormBuilder, 
    private snackBar : MatSnackBar,
    private dialogRef: MatDialogRef<PurchaseComponent>,
    private museumService : MuseumService,
    @Inject(MAT_DIALOG_DATA) data : any) { 
      this.virtualVisit = data.virtualVisit;
    }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: [null, Validators.required],
      surname :[null, Validators.required],
      cardNumber :[null, Validators.required],
      cardType :[null, Validators.required],
      expirationDate :[null, Validators.required],
      pin : [null, Validators.required]
    });
  }

  buyTicket(purchaseRequest : PurchaseRequest) {
   
    console.log(JSON.stringify(purchaseRequest));
    console.log(JSON.stringify(this.virtualVisit));

    this.buyTicketClicked = true;
      this.museumService.purchaseTicket(purchaseRequest, this.virtualVisit).subscribe(data => {
        this.form.reset()
        this.snackBar.open("You have successfully purchased a ticket!", undefined, { 
          duration: 2000,
        });
        this.buyTicketClicked = false;
        this.close();
      },
      error => {
        console.log(JSON.stringify(error));
        this.snackBar.open(error.error, undefined, { 
          duration: 2000,
        });
        this.buyTicketClicked = false;
      });
  }

  close() {
    this.dialogRef.close();
  }


}
