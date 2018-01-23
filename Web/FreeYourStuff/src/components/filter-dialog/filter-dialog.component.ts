import { Component, OnInit } from '@angular/core';
import { Category } from '../../models/item/item';
import { DataService } from '../../services/data/data.service';
import { ServerService } from '../../services/server/server.service';
import { MatDialogRef } from '@angular/material';
import { catchError, map, tap } from 'rxjs/operators';

@Component({
  selector: 'app-filter-dialog',
  templateUrl: './filter-dialog.component.html',
  styleUrls: ['./filter-dialog.component.css']
})
export class FilterDialogComponent implements OnInit {
  optionsCategory: string[];
  categories = Category;

  distance = this.data.filters.distance;
  category = this.data.filters.category;
  availability = this.data.filters.availability;

  constructor(public dialogRef: MatDialogRef<FilterDialogComponent>, public data: DataService, public server: ServerService) {
    this.optionsCategory = Object.keys(this.categories).filter(Number);
  }

  ngOnInit() {
  }

  onFilter() {
    this.data.filters = {
      'distance': this.distance,
      'category': this.category,
      'availability': this.availability
    };

    this.server.getItems().subscribe();

    this.dialogRef.close();
  }

}
