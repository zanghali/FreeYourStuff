import { Component, OnInit } from '@angular/core';
import { Status, Item } from '../../models/item/item';
import { ServerService } from '../../services/server/server.service';
import { DataService } from '../../services/data/data.service';
import { MatDialog, MatSnackBarConfig, MatSnackBar } from '@angular/material';
import { ItemDialogComponent } from '../item-dialog/item-dialog.component';
import { InterestedPeopleDialogComponent } from '../interested-people-dialog/interested-people-dialog.component';

@Component({
  selector: 'app-my-offers',
  templateUrl: './my-offers.component.html',
  styleUrls: ['./my-offers.component.css']
})
export class MyOffersComponent implements OnInit {
  status = Status;
  options: string[];

  constructor(public server: ServerService, public data: DataService, public dialog: MatDialog, public snackBar: MatSnackBar) {
    this.options = Object.keys(this.status).filter(Number);
  }

  ngOnInit() {
    this.server.getItemsByUser().subscribe();
  }

  isOffersEmpty() {
    return (!Object.keys(this.data.offers).length);
  }

  onClickOffer(offer): void {
    let dialogRef = this.dialog.open(ItemDialogComponent, {
      data: {
        item: offer
      }
    });
  }

  onClickPeople(item): void {
    this.server.getUserInterestedByItem(item.id_item, (error, data) => {  
      if (error)
        console.log(error);
      else if (!Object.keys(data).length) {
        let config = new MatSnackBarConfig();
        config.extraClasses = ['custom-class'];
        config.duration = 3000;
    
        let snackBarRef = this.snackBar.open("Vous n'avez reçu aucune demande pour cet article !", "", config);  
      }
      else {
        let dialogRef = this.dialog.open(InterestedPeopleDialogComponent, {
          data: {
            people: data
          }
        });
      }
    });
  }

  onClickDelete(item): void {
    let config = new MatSnackBarConfig();
    config.extraClasses = ['custom-class'];
    config.duration = 5000;

    let snackBarRef = this.snackBar.open("Etes-vous sûr de vouloir supprimer cette annonce ?", "Oui", config);

    snackBarRef.onAction().subscribe(() => {
      this.server.deleteItem(item)
        .subscribe(_ => {
          config.duration = 3000;

          this.snackBar.open("L'annonce a bien été supprimée !", "", config);
        });
    });
  }
}
