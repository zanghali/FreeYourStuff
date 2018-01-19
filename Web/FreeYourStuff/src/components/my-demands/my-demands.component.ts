import { Component, OnInit } from '@angular/core';
import { Item, Status } from '../../models/item/item';
import { ServerService } from '../../services/server/server.service';
import { DataService } from '../../services/data/data.service';
import { MatDialog } from '@angular/material';
import { ItemDialogComponent } from '../item-dialog/item-dialog.component';

@Component({
  selector: 'app-my-demands',
  templateUrl: './my-demands.component.html',
  styleUrls: ['./my-demands.component.css']
})
export class MyDemandsComponent implements OnInit {
  status = Status;
  options: string[];

  constructor(public dialog: MatDialog, public server: ServerService, public data: DataService) {
    this.options = Object.keys(this.status).filter(Number);
  }

  ngOnInit() {
    this.server.getItemOfUserInterestedBy().subscribe();
  }

  isDemandsEmpty() {
    return (!Object.keys(this.data.demands).length);
  }

  onClickDemand(demand): void {
    let dialogRef = this.dialog.open(ItemDialogComponent, {
      data: {
        item: demand
      }
    });
  }
}