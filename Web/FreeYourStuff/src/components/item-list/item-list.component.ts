import { Component, OnInit, Input } from '@angular/core';
import {MatDialog} from '@angular/material';
import { Item } from '../../models/item/item';
import { ItemCreationDialogComponent } from '../../components/item-creation-dialog/item-creation-dialog.component';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  @Input() items: Item[];

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  creationDialog(): void {
    let dialogRef = this.dialog.open(ItemCreationDialogComponent, {
      // width: '250px'
    });

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
    });
  }
}
