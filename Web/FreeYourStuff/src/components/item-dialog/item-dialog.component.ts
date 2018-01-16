import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { Item, Category, Status, Availability } from '../../models/item/item';
import { MapsAPILoader } from '@agm/core';
import { AuthService } from '../../services/auth/auth.service';
import { ServerService } from '../../services/server/server.service';
import { DataService } from '../../services/data/data.service';

declare var google;

@Component({
  selector: 'app-item-dialog',
  templateUrl: './item-dialog.component.html',
  styleUrls: ['./item-dialog.component.css']
})
export class ItemDialogComponent implements OnInit {
  item: Item;

  constructor(public dialogRef: MatDialogRef<ItemDialogComponent>, @Inject(MAT_DIALOG_DATA) public input: any, public snackBar: MatSnackBar, private mapsAPILoader: MapsAPILoader, public auth: AuthService, public data: DataService, public server: ServerService) {
    this.item = this.input.item;
  }

  ngOnInit() {
  }

  onBack(): void {
    this.dialogRef.close();
  }

  onClick(): void {
    let config = new MatSnackBarConfig();
    config.extraClasses = ['custom-class'];
    config.duration = 3000;

    if (this.auth.isAuthenticated()) {
      this.server.setUserInterestedByItem(this.data.getUser().id, this.item.id_item, (error, data) => {  
        if (error)
          console.log(error);
        else if (data == true) {
          this.snackBar.open("Votre demande a bien été enregistrée pour l'article : " + this.item.title, "", config);
          this.dialogRef.close();
        }
        else
          this.snackBar.open("Vous avez déjà effectué une demande pour cet article !", "", config);
      });
    }
    else {
      let snackBarRef = this.snackBar.open("Vous devez être connecté pour ajouter un article !", "Log In", config);

      snackBarRef.onAction().subscribe(() => {
        this.auth.login();
      });
    }
  }

  // Helpers

  capitalize(input) {
    return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
  }

  ownItem() {
    return (this.item.id_user == this.data.getUser().id);
  }
}