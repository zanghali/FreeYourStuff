import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { Item, Status } from '../../models/item/item';
import { ServerService } from '../../services/server/server.service';
import { DataService } from '../../services/data/data.service';
import { ItemDialogComponent } from '../item-dialog/item-dialog.component';

@Component({
  selector: 'app-personal-space-dialog',
  templateUrl: './personal-space-dialog.component.html',
  styleUrls: ['./personal-space-dialog.component.css']
})
export class PersonalSpaceDialogComponent implements OnInit {
  selectedindex: number;

  constructor(public dialogRef: MatDialogRef<PersonalSpaceDialogComponent>, @Inject(MAT_DIALOG_DATA) public input: any) {
    this.selectedindex = this.input.index;
  }

  ngOnInit() {

  }

  onBack(): void {
    this.dialogRef.close();
  }
}
