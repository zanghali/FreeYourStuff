import { Component, OnInit, Input } from '@angular/core';
import { MatDialog, MatSnackBarConfig, MatSnackBar } from '@angular/material';
import { Item } from '../../models/item/item';
import { ItemCreationDialogComponent } from '../../components/item-creation-dialog/item-creation-dialog.component';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  @Input() items: Item[];

  constructor(public dialog: MatDialog, public auth: AuthService, public snackBar: MatSnackBar) { }

  ngOnInit() {
  }

  creationDialog(): void {
    if (this.auth.isAuthenticated()) {
      let dialogRef = this.dialog.open(ItemCreationDialogComponent);

      dialogRef.afterClosed().subscribe(result => {
        // console.log('The dialog was closed');
      });
    }
    else {
      let config = new MatSnackBarConfig();
      config.extraClasses = ['custom-class'];
      config.duration = 3000;
      let snackBarRef = this.snackBar.open("Vous devez être connecté pour ajouter un article !", "Log In", config);

      snackBarRef.onAction().subscribe(() => {
        this.auth.login();
      });
    }
  }
}
