import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { MatDialog } from '@angular/material';
import { FilterDialogComponent } from '../filter-dialog/filter-dialog.component';
import { DataService } from '../../services/data/data.service';
import { ServerService } from '../../services/server/server.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  search: String = "";

  constructor(public dialog: MatDialog, public auth: AuthService, public data: DataService, public server: ServerService) { }

  ngOnInit() {
  }

  onSearch() {
    this.data.search = this.search;
    this.server.getItems().subscribe();
  }

  onFilter(): void {
    let dialogRef = this.dialog.open(FilterDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
    });
  }
}
