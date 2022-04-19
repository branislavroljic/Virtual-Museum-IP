import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.css']
})
export class ConfirmDialogComponent implements OnInit {

  message: string = "Are you sure?"
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<ConfirmDialogComponent>
  ) { 
    if(data){
      this.message = data.message || this.message;
    }
  }

  ngOnInit(): void {
  }

  confirmClick(): void {
    this.dialogRef.close(true);
  }

  cancelClick() : void{
    this.dialogRef.close(false);
  }
}
