import { Component, OnInit } from '@angular/core';
import { Item, Status } from '../../models/item/item';
import { ServerService } from '../../services/server/server.service';
import { DataService } from '../../services/data/data.service';
import { MatDialog, MatSnackBarConfig, MatSnackBar } from '@angular/material';
import { ItemDialogComponent } from '../item-dialog/item-dialog.component';
import { ChatDialogComponent } from '../chat-dialog/chat-dialog.component';

@Component({
  selector: 'app-my-demands',
  templateUrl: './my-demands.component.html',
  styleUrls: ['./my-demands.component.css']
})
export class MyDemandsComponent implements OnInit {
  status = Status;
  options: string[];

  constructor(public dialog: MatDialog, public server: ServerService, public data: DataService, public snackBar: MatSnackBar) {
    this.options = Object.keys(this.status).filter(Number);
  }

  ngOnInit() {
    this.server.getItemOfUserInterestedBy().subscribe();
  }

  isDemandsEmpty() {
    return (!Object.keys(this.data.demands).length);
  }

  onClickDemand(demand): void {
    let dialogRef = this.dialog.open(ItemDialogComponent, {
      data: {
        item: demand
      }
    });
  }

  onChat(demand: Item): void {
    this.server.getUserById(demand.id_user, (error, data) => {
      if (error)
        console.log(error);
      // No User found in db
      else if (Object.keys(data).length) {
        // this.dialogRef.close();
        let dialogRef = this.dialog.open(ChatDialogComponent, {
          data: {
            person: data[0],
            item: demand
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
      this.server.deleteUserInterestedByItem(item.id_item)
        .subscribe(_ => {
          config.duration = 3000;

          this.snackBar.open("La demande a bien été annulée !", "", config);
        });
    });
  }
}