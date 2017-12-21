import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  myLatitude: number;
  myLongitude: number;

  markers = [
    {
      lat: 45.772828,
      lng: 4.859574,
      label: 'A'
    },
    {
      lat: 45.772611,
      lng: 4.865790,
      label: 'B'
    }
  ]

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
