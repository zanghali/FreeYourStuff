import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { AuthService } from '../../services/auth/auth.service';
import { ProfileDialogComponent } from '../../components/profile-dialog/profile-dialog.component';
import { PersonalSpaceDialogComponent } from '../../components/personal-space-dialog/personal-space-dialog.component';
import { DataService } from '../../services/data/data.service';

@Component({
  selector: 'app-menu-auth',
  templateUrl: './menu-auth.component.html',
  styleUrls: ['./menu-auth.component.css']
})
export class MenuAuthComponent implements OnInit {
  nickname: string;
  photo: string;

  constructor(public auth: AuthService, public data: DataService, public dialog: MatDialog) { }

  ngOnInit() {
    this.auth.getProfile((err) => {
      this.nickname = localStorage.getItem("nickname");
      this.photo = localStorage.getItem("photo");
    });
  }

  profileDialog(): void {
    let dialogRef = this.dialog.open(ProfileDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
    });
  }
  
  OffersDialog(): void {
    let dialogRef = this.dialog.open(PersonalSpaceDialogComponent, {
      data: {
        index: 0
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
    });
  }
  
  RequestsDialog(): void {
    let dialogRef = this.dialog.open(PersonalSpaceDialogComponent, {
      data: {
        index: 1
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
    });
  }

}
