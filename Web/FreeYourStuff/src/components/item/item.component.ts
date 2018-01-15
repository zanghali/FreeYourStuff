import { Component, OnInit, Input } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Item, Category, Status, Availability } from '../../models/item/item';
import { ItemDialogComponent } from '../../components/item-dialog/item-dialog.component';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {
  @Input() item: Item;

  constructor(public dialog: MatDialog) {
  }

  ngOnInit() {
  }

  onClick(): void {
    let dialogRef = this.dialog.open(ItemDialogComponent, {
      data: {
        item: this.item
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
    });
  }
}
