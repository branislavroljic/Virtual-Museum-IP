import { ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { merge, Observable, of, startWith, switchMap } from 'rxjs';
import { Item, RssData } from 'src/app/model/rss.model';
import { RssService } from '../rss.service';

@Component({
  selector: 'app-rss',
  templateUrl: './rss.component.html',
  styleUrls: ['./rss.component.css']
})
export class RssComponent implements OnInit, OnDestroy {

  news: RssData | null = null;
  @ViewChild(MatPaginator) paginator: MatPaginator | null = null;
  obs: Observable<any> = new Observable<any>();
  dataSource: MatTableDataSource<Item> | undefined;

  constructor(private rssService: RssService, private changeDetectorRef: ChangeDetectorRef) {

  }

  ngOnInit(): void {
    this.rssService.getCultureNews().subscribe(data => {
      this.news = data;
      this.dataSource = new MatTableDataSource<Item>(this.news.items);
      this.changeDetectorRef.detectChanges();
      this.dataSource.paginator = this.paginator;
      this.obs = this.dataSource.connect();
    });;

  }

  ngOnDestroy() {
    if (this.dataSource) {
      this.dataSource.disconnect();
    }
  }
}
