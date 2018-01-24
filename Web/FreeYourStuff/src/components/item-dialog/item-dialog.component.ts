import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { Item, Category, Status, Availability } from '../../models/item/item';
import { MapsAPILoader } from '@agm/core';
import { AuthService } from '../../services/auth/auth.service';
import { ServerService } from '../../services/server/server.service';
import { DataService } from '../../services/data/data.service';
import { User } from '../../models/user/user';

declare var google;

@Component({
  selector: 'app-item-dialog',
  templateUrl: './item-dialog.component.html',
  styleUrls: ['./item-dialog.component.css']
})
export class ItemDialogComponent implements OnInit {
  item: Item;
  nbPeople = 0;
  isAlreadyAdded = false;

  constructor(public dialogRef: MatDialogRef<ItemDialogComponent>, @Inject(MAT_DIALOG_DATA) public input: any, public snackBar: MatSnackBar, private mapsAPILoader: MapsAPILoader, public auth: AuthService, public data: DataService, public server: ServerService) {
    this.item = this.input.item;
  }

  ngOnInit() {
    // Sets the number of people interested in this item
    this.server.getNumberInterestedByItem(this.item.id_item, (error, data) => {
      if (error)
        console.log(error);
      else if (Object.keys(data).length) {
        this.nbPeople = data[0].count;
      }
    });
    // Sets the state of the item for the user
    this.server.getUserInterestedByItem(this.item.id_item, (error, data) => {
      if (error)
        console.log(error);
      else if (Object.keys(data).length) {
        let result = data.filter(user => {
          return (user.id_user == this.data.getUser().id_user);
        });

        if (Object.keys(result).length)
          this.isAlreadyAdded = true;
      }
    });
  }

  onBack(): void {
    this.dialogRef.close();
  }

  onClick(): void {
    let config = new MatSnackBarConfig();
    config.extraClasses = ['custom-class'];
    config.duration = 3000;

    if (this.auth.isAuthenticated()) {
      if (!this.isAlreadyAdded) {
        this.server.setUserInterestedByItem(this.item.id_item, (error, data) => {
          if (error)
            console.log(error);
          else if (data == true) {
            this.snackBar.open("Votre demande a bien été enregistrée pour l'article : " + this.item.title, "", config);
            this.isAlreadyAdded = true;
          }
        });
      }
      else {
        this.server.deleteUserInterestedByItem(this.item.id_item).subscribe(_ => {
          this.snackBar.open("L'article a été retiré de vos demandes !", "", config);
          this.isAlreadyAdded = false;
        });
      }
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
    return (this.item.id_user == this.data.getUser().id_user);
  }

  getIcon(): String {
    return this.isAlreadyAdded ? 'done' : 'add_shopping_cart';
  }

  getTooltip(): String {
    return this.isAlreadyAdded ? "Retirer l'article de vos demandes !" : "Faire une demande pour cet article ";
  }
}