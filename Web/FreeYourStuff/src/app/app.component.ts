import { Component } from '@angular/core';
import { Item } from '../models/item/item';
import { ServerService } from '../services/server/server.service';
import { AuthService } from '../services/auth/auth.service';
import { DataService } from '../services/data/data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  items: Item[];

  constructor(private server: ServerService, public auth: AuthService, public data: DataService) {
    auth.handleAuthentication();
  }
  
  getItems() {
    // Current Location
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.data.myLatitude = position.coords.latitude;
        this.data.myLongitude = position.coords.longitude;

        this.server.getItems(position.coords.latitude, position.coords.longitude, 100000000)
          .subscribe(items => {
            console.log('!!!');
            this.items = items;
          });
      });
    }
  }

  ngOnInit() {
    this.getItems();
  }
}
