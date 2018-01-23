import { Component, OnInit, Inject } from '@angular/core';
import { User } from '../../models/user/user';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material';
import { ChatDialogComponent } from '../chat-dialog/chat-dialog.component';
import { Item } from '../../models/item/item';

@Component({
  selector: 'app-interested-people-dialog',
  templateUrl: './interested-people-dialog.component.html',
  styleUrls: ['./interested-people-dialog.component.css']
})
export class InterestedPeopleDialogComponent implements OnInit {
  item: Item;
  people: User[];
  
  constructor(public dialogRef: MatDialogRef<InterestedPeopleDialogComponent>, @Inject(MAT_DIALOG_DATA) public input: any, public dialog: MatDialog) {
    this.item = this.input.item;
    this.people = this.input.people;
  }

  ngOnInit() {
  }

  onChat(person: User): void {
    // this.dialogRef.close();
    let dialogRef = this.dialog.open(ChatDialogComponent, {
      data: {
        person: person,
        item: this.item
      }
    });
  }

  onBack(): void {
    this.dialogRef.close();
  }

}
