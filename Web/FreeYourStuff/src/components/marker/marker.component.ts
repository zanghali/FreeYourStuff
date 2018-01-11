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
  latitude: number;
  longitude: number;
  iconUrl: string;
  availability: string;

  constructor(private mapsAPILoader: MapsAPILoader, public dialog: MatDialog) { }

  //Component Lifecycle

  ngOnInit() {
    this.availability = Availability[this.item.availability];
    this.setIcon();
    this.setCoordinate(this);
  }

  onClick(): void {
    let dialogRef = this.dialog.open(ItemDialogComponent, {
      data: {
        item: this.item,
        availability: this.availability
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
    });
  }

  // ngOnChanges(changes: SimpleChanges) {
  //   if (changes['item']) {
  //   }
  // }

  // Helpers

  setIcon() {
    var cat;

    switch (this.item.category) {
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
    }

    this.iconUrl = "https://storage.googleapis.com/material-icons/external-assets/v4/icons/svg/ic_" + cat + "_black_36px.svg";
  }

  setCoordinate(that) {
    this.mapsAPILoader.load().then(() => {
      var geocoder = new google.maps.Geocoder();

      geocoder.geocode({ 'address': this.item.address }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
          that.latitude = results[0].geometry.location.lat();
          that.longitude = results[0].geometry.location.lng();
        }
      });
    });
  }

}
