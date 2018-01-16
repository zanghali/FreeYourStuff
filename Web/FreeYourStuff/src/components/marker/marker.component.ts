import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material';
import { MapsAPILoader } from '@agm/core';
import { Item, Category, Availability } from '../../models/item/item';
import { ItemDialogComponent } from '../../components/item-dialog/item-dialog.component';
import { DataService } from '../../services/data/data.service';

declare var google;

@Component({
  selector: 'app-marker',
  templateUrl: './marker.component.html',
  styleUrls: ['./marker.component.css']
})
export class MarkerComponent implements OnInit {
  @Input() item: Item;
  iconUrl: string;
  latitude: number;
  longitude: number;

  constructor(private mapsAPILoader: MapsAPILoader, public dialog: MatDialog, public data: DataService) { }

  ngOnInit() {
    this.latitude = +this.item.gps.split(',')[0];
    this.longitude = +this.item.gps.split(',')[1];
    this.iconUrl = "https://storage.googleapis.com/material-icons/external-assets/v4/icons/svg/ic_" + this.data.getCategoryIcon(this.item.category) + "_black_36px.svg";
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
