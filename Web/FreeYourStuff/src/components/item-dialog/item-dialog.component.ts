import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { Item, Category, Status, Availability } from '../../models/item/item';
import { MapsAPILoader } from '@agm/core';

declare var google;

@Component({
  selector: 'app-item-dialog',
  templateUrl: './item-dialog.component.html',
  styleUrls: ['./item-dialog.component.css']
})
export class ItemDialogComponent implements OnInit {
  item: Item;
  availability: string;
  categories = Category;
  latitude: number;
  longitude: number;

  constructor(public dialogRef: MatDialogRef<ItemDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any, public snackBar: MatSnackBar, private mapsAPILoader: MapsAPILoader) {
    this.item = this.data.item;
    this.availability = this.data.availability;
  }

  ngOnInit() {
    this.setCoordinate(this);
  }

  onClick(): void {
    var response;

    this.dialogRef.close();

    // Request to server + set reponse depending on the result :
    if (true)
      response = "Votre demande a bien été enregistrée pour l'article : " + this.item.title;
    else
      response = "Votre demande n'a pas être enregistrée. Veuillez réessayer plus tard.";

    this.snackBar.open(response, "Ok", {
      duration: 2000
    });
  }

  // Helpers

  capitalize(input) {
    return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
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