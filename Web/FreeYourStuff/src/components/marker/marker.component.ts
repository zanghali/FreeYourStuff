import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material';
import { MapsAPILoader } from '@agm/core';
import { Item, Category, Availability } from '../../models/item/item';
import { ItemDialogComponent } from '../../components/item-dialog/item-dialog.component';

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

  constructor(private mapsAPILoader: MapsAPILoader, public dialog: MatDialog) { }

  //Component Lifecycle

  ngOnInit() {
    this.setIcon();
    this.latitude = +this.item.gps.split(',')[0];
    this.longitude = +this.item.gps.split(',')[1];
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

  // Helpers

  setIcon() {
    var cat;

    switch (<any>Category[this.item.category]) {
      case Category.animal:
        cat = "pets";
        break;
      case Category.game:
        cat = "videogame_asset";
        break;
      case Category.furniture:
        cat = "weekend";
        break;
      case Category.music:
        cat = "music_note";
        break;
      case Category.multimedia:
        cat = "video_library";
        break;
      case Category.food:
        cat = "restaurant";
        break;
      case Category.sport:
        cat = "directions_bike";
        break;
      case Category.book:
        cat = 'import_contacts';
        break;
      case Category.clothing:
        cat = 'shopping_basket';
        break;
      case Category.other:
        cat = 'flag';
        break;
    }

    this.iconUrl = "https://storage.googleapis.com/material-icons/external-assets/v4/icons/svg/ic_" + cat + "_black_36px.svg";
  }

}
