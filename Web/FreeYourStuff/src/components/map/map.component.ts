import { Component, OnInit, Input } from '@angular/core';
import { Item } from '../../models/item/item';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  @Input() items: Item[];
  myLatitude: number;
  myLongitude: number;

  constructor() { }

  ngOnInit() {
    // Current Location
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.myLatitude = position.coords.latitude;
        this.myLongitude = position.coords.longitude;
      });
    }
  }
}
