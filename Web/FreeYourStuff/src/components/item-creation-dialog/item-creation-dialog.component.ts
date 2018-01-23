import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { FileUploader } from 'ng2-file-upload';
import { Category, Availability } from '../../models/item/item';
import { ServerService } from '../../services/server/server.service';
import { DataService } from '../../services/data/data.service';

@Component({
  selector: 'app-item-creation-dialog',
  templateUrl: './item-creation-dialog.component.html',
  styleUrls: ['./item-creation-dialog.component.css']
})
export class ItemCreationDialogComponent implements OnInit {
  creationForm: FormGroup;
  categories = Category;
  options: string[];
  uploader: FileUploader;
  photo: string = '';

  constructor(public dialogRef: MatDialogRef<ItemCreationDialogComponent>, private server: ServerService, public data: DataService, public snackBar: MatSnackBar) {
    this.options = Object.keys(this.categories).filter(Number);

    this.uploader = new FileUploader({
      url: this.server.SERVER_URL + "upload",
      itemAlias: "photo"
    });
    this.uploader.onAfterAddingFile = (file) => { file.withCredentials = false; };
    this.uploader.onBuildItemForm = (item, form) => {
      form.append("id_user", this.data.getUser().id_user);
    };
    this.uploader.response.subscribe(path => {
      console.log(path);
      this.photo = path
    });
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

  onBack(): void {
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.creationForm.valid) {
      var json = this.creationForm.value;

      json.category = json.category.toLowerCase();
      json.availability = this.data.availabilityToEnum(json.availability);
      json.photo = this.photo;
      json.id_user = this.data.getUser().id_user;

      this.server.addItem(json)
        .subscribe(_ => {
          this.dialogRef.close();

          let config = new MatSnackBarConfig();
          config.extraClasses = ['custom-class'];
          config.duration = 3000;
          this.snackBar.open("L'annonce a bien été enregistrée !", "", config);
        });
    }
  }
}
