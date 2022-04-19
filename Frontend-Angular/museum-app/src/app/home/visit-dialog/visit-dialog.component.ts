import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { VirutalVisit } from 'src/app/model/virtual-visit.model';
import { MuseumDialogComponent } from '../museum-dialog/museum-dialog.component';
import { MuseumService } from '../services/museum.service';
import * as _moment from 'moment';
const moment = _moment;


export function conditionalValidator(field: string): ValidatorFn {
  return formControl => {
    if (!formControl.parent) {
      return null;
    }
    const otherControl = formControl.parent.get(field);
    const error =
      formControl.value || otherControl?.value
        ? null
        : { error: 'You must upload a video or provide YT link!' };
    if ((error && otherControl?.valid) || (!error && otherControl?.invalid)) {
      setTimeout(() => {
        otherControl.updateValueAndValidity({ emitEvent: false });
      });
    }
    return error;
  };
}

@Component({
  selector: 'app-visit-dialog',
  templateUrl: './visit-dialog.component.html',
  styleUrls: ['./visit-dialog.component.css']
})
export class VisitDialogComponent implements OnInit {

  form: FormGroup = new FormGroup({});
  selectedFiles: FileList | null = null;
  selectedMP4Video: File | null = null;
  error: string = '';
  museumId: number;

  minDate: Date;

  constructor(private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<MuseumDialogComponent>,
    private dialog: MatDialog,
    private matSnackBar: MatSnackBar,
    private museumService: MuseumService,
    @Inject(MAT_DIALOG_DATA) data: any) {
    this.museumId = data.museumId;

    const currentDate = new Date();
    this.minDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate());
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      date: [null, Validators.required],
      startTime: [null, Validators.required],
      duration: [null, Validators.required],
      price: [null, Validators.required],
      images: [null, Validators.required],
      mp4Video: [null, conditionalValidator("ytVideo")],
      ytVideo: [null, conditionalValidator("mp4Video")]
    });
  }


  addVisit() {

    const formData = new FormData();

    formData.append('date', moment(this.form.value.date).format("YYYY-MM-DD"));
    formData.append('startTime', this.form.value.startTime);
    formData.append('duration', this.form.value.duration);
    formData.append('price', this.form.value.price);

    if (this.selectedFiles && this.selectedFiles.length >= 5 && this.selectedFiles.length <= 10)
      for (let i = 0; i < this.selectedFiles.length; i++) {
        formData.append('files[]', this.selectedFiles[i], this.selectedFiles[i].name);
      }
    else {
      this.error = 'You must upload 5-10 images!'
      return;
    };

    if (this.selectedMP4Video)
      formData.append('mp4Video', this.selectedMP4Video, this.selectedMP4Video.name);
    else
      formData.append('ytVideo', this.form.value.ytVideo);

    this.museumService.addVirtualVisit(this.museumId, formData).subscribe(
      data => {
        this.dialogRef.close(data);
        this.matSnackBar.open("Virtual visit added successfully!", undefined, {
          duration: 2000
        });
      },
      err => {
        alert(err.error);
      }
    );

  }

  selectFiles(event: any) {
    this.selectedFiles = event.target.files;
    console.log(this.selectedFiles?.length);
  }

  selectMP4Video(event: any) {
    this.selectedMP4Video = event.target.files[0];
  }

  close() {
    this.dialogRef.close();
  }
}
