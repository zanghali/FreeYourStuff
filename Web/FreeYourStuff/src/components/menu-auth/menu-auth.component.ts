import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { AuthService } from '../../services/auth/auth.service';
import { ProfileDialogComponent } from '../../components/profile-dialog/profile-dialog.component';
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
      this.nickname = this.data.getUser().nickname;
      this.photo = this.data.getUser().photo;
    });
  }

  profileDialog(): void {
    let dialogRef = this.dialog.open(ProfileDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
    });
  }

}
