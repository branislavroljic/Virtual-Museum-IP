import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Chart } from 'chart.js';
import { Observable } from 'rxjs';
import { Report } from 'src/app/model/report.model';
import { ReportService } from '../services/report.service';
@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.css']
})
export class HomeAdminComponent implements AfterViewInit, OnInit, OnDestroy {

  @ViewChild('lineCanvas') private lineCanvas!: ElementRef;

  lineChart: any;
  loggedInCount: number | null = null;
  registeredCount: number | null = null;
  reports: Report[] = [];

  timer: any;

  constructor(private reportService: ReportService) {
  }

  ngOnInit(): void {
    this.timer = setInterval(() => {
        this.getNumberOfRegisteredAndLoggedIn();
    }, 10000);
  }

  ngOnDestroy(): void {
    clearInterval(this.timer);
  }

  ngAfterViewInit(): void {
    this.getNumberOfRegisteredAndLoggedIn();

    this.reportService.get24HReport().subscribe(data => {
      this.reports = data;
      this.lineChart = new Chart(this.lineCanvas.nativeElement, {
        type: 'line',
        data: {
          labels: this.reports.map(r => r.date + " at: " + r.hour + "h"),
          datasets: [{
            label: '# of logins for the last 24 fours',
            data: this.reports.map(r => r.count),
            borderWidth: 1,
            backgroundColor: "blue",
            borderColor: "lightblue",
            tension: 0,
            pointRadius: 5
          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });
    })
  }
  getNumberOfRegisteredAndLoggedIn() {
    this.reportService.countLoggedInUsers().subscribe(data => {
      this.loggedInCount = data;
    })
    this.reportService.countRegisteredUsers().subscribe(data => {
      this.registeredCount = data;
    })
  }

}
