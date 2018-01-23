import { Component, OnInit, Input } from '@angular/core';
import { Item } from '../../models/item/item';
import { ZoomControlOptions, ControlPosition, ZoomControlStyle, MapTypeStyle } from '@agm/core/services/google-maps-types';
import { DataService } from '../../services/data/data.service';

declare var google: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  @Input() items: Item[];
  myLatitude: number;
  myLongitude: number;

  map: any;
  zoom: ZoomControlOptions = {
    position: ControlPosition.TOP_LEFT,
    style: ZoomControlStyle.LARGE
  };

  constructor(public data: DataService) {}

  ngOnInit() {
    // Current Location
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.myLatitude = position.coords.latitude;
        this.myLongitude = position.coords.longitude;
      });
    }
  }

  onMapReady(map) {
    this.map = map;
  }

  onCenter() {
    const position = new google.maps.LatLng(this.data.myLatitude, this.data.myLongitude);
    this.map.panTo(position);
    this.map.setZoom(15);
  }
}
