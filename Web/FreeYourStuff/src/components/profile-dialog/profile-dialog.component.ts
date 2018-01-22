import { Component, OnInit, Inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { MapsAPILoader } from '@agm/core';
import { User } from '../../models/user/user';
import { DataService } from '../../services/data/data.service';
import { ServerService } from '../../services/server/server.service';

declare var google;

@Component({
  selector: 'app-profile-dialog',
  templateUrl: './profile-dialog.component.html',
  styleUrls: ['./profile-dialog.component.css']
})
export class ProfileDialogComponent implements OnInit {
  user: User = this.data.user;

  latitude: number;
  longitude: number;

  constructor(public server: ServerService, public data: DataService, public dialogRef: MatDialogRef<ProfileDialogComponent>, public snackBar: MatSnackBar, private mapsAPILoader: MapsAPILoader) {
  }

  ngOnInit() {
    console.log(this.data.user);

    this.checkAddress();
  }

  onUpdate() {
    localStorage.setItem('firstname', this.user.firstname);
    localStorage.setItem('lastname', this.user.lastname);
    localStorage.setItem('phone', this.user.phone);
    localStorage.setItem('email', this.user.email);
    localStorage.setItem('address', this.user.address);

    this.server.updateUser((error, data) => {  
      let config = new MatSnackBarConfig();
      config.extraClasses = ['custom-class'];
      config.duration = 2000;

      if (error)
        console.log(error);
      else if (data == true) {
        this.snackBar.open("Votre profil a bien été mis à jour !", "", config);
        this.dialogRef.close();
      }
      else
        this.snackBar.open("Votre profil n'a pas pu être mis à jour :(", "", config);
    });
  }

  checkAddress() {
    var that = this;

    this.mapsAPILoader.load().then(() => {
      var geocoder = new google.maps.Geocoder();

      geocoder.geocode({ 'address': this.user.address }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
          that.latitude = results[0].geometry.location.lat();
          that.longitude = results[0].geometry.location.lng();
        }
      });
    });
  }

}
