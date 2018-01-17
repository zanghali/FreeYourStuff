import { Component, OnInit, Inject } from '@angular/core';
import { User } from '../../models/user/user';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-interested-people-dialog',
  templateUrl: './interested-people-dialog.component.html',
  styleUrls: ['./interested-people-dialog.component.css']
})
export class InterestedPeopleDialogComponent implements OnInit {
  people: User[];
  
  constructor(public dialogRef: MatDialogRef<InterestedPeopleDialogComponent>, @Inject(MAT_DIALOG_DATA) public input: any) {
    this.people = this.input.people;
  }

  ngOnInit() {
    console.log(this.people);
  }

  onBack(): void {
    this.dialogRef.close();
  }

}
