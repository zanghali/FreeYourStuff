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
  availability: string;

  constructor(public dialog: MatDialog) {
  }

  ngOnInit() {
    this.availability = Availability[this.item.availability];
  }

  onClick(): void {
    let dialogRef = this.dialog.open(ItemDialogComponent, {
      data: {
        item: this.item,
        availability: this.availability
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
