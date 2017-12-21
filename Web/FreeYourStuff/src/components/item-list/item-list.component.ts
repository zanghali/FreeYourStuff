import { Component, OnInit } from '@angular/core';
import { Item, Category, Status, Availability } from '../../models/item/item';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  items: Item[] = [
    {
      category: Category.animal,
      title: 'chien 1',
      description: 'A donner 1',
      photo: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
      address: '',
      phone: 0,
      status: Status.waiting,
      creationDate: new Date(),
      location: '1.0',
      availability: Availability.asap,
      user: null
    },
    {
      category: Category.animal,
      title: 'chien 2',
      description: 'A donner 2',
      photo: 'https://material.angular.io/assets/img/examples/shiba2.jpg',
      address: '',
      phone: 0,
      status: Status.waiting,
      creationDate: new Date(),
      location: '1.3',
      availability: Availability.asap,
      user: null
    }
  ];

  constructor() { }

  ngOnInit() {
  }

}
