import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Log } from 'src/app/model/log.model';
import { LogsService } from '../services/logs.service';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css']
})
export class LogsComponent implements OnInit {

  displayedColumns: string[] = ['dateTime','user',  'message', 'type',];
  logsDataSource = new MatTableDataSource<Log>();

  constructor(private logsService: LogsService) { }

  ngOnInit(): void {

    this.logsService.getAllLogs().subscribe(
      data => {
        this.logsDataSource.data = data;
      },
      err => {
        alert(err);
      }
    );
  }

  downloadLogsClick(){
    this.logsService.downloadLogs().subscribe(blob => saveAs(blob, "logs.pdf"));
  }
}
