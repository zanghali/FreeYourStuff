import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { Category, Availability } from '../../models/item/item';
import { ServerService } from '../../services/server/server.service';

@Component({
  selector: 'app-item-creation-dialog',
  templateUrl: './item-creation-dialog.component.html',
  styleUrls: ['./item-creation-dialog.component.css']
})
export class ItemCreationDialogComponent implements OnInit {
  creationForm: FormGroup;
  categories = Category;
  options: string[];

  constructor(public dialogRef: MatDialogRef<ItemCreationDialogComponent>, private serverService: ServerService, public snackBar: MatSnackBar) {
    this.options = Object.keys(this.categories).filter(Number)
  }

  ngOnInit() {
    this.creationForm = new FormGroup({
      category: new FormControl(),
      title: new FormControl(),
      description: new FormControl(),
      address: new FormControl(),
      phone: new FormControl(),
      availability: new FormControl()
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.creationForm.valid) {
      var json = this.creationForm.value;

      json.category = json.category.toLowerCase();
      json.availability = this.availabilityToEnum(json.availability);

      this.serverService.createItem(json);

      this.dialogRef.close();

      let config = new MatSnackBarConfig();
      config.extraClasses = ['custom-class'];
      config.duration = 2000;
      this.snackBar.open("L'annonce a bien été enregistrée !", "Ok", config);
    }
  }

  // Helpers

  capitalize(input) {
    return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
  }

  availabilityToEnum(input) {
    switch (input) {
      case "Dès aujourd'hui":
        return Availability[Availability.asap];
      case "Dans les jours à venir":
        return Availability[Availability.upcomingDays];
      case "Dans les semaines à venir":
        return Availability[Availability.upcomingWeeks];
    }
  }

}
