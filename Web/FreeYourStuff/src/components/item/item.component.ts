import { Component, OnInit, Input } from '@angular/core';
import { Item, Category, Status, Availability } from '../../models/item/item';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {
  @Input() item: Item;
  availability: string;

  constructor() {
  }

  ngOnInit() {
    this.availability = Availability[this.item.availability];
  }
}
