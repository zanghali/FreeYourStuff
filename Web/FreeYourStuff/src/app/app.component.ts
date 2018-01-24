import { Component } from '@angular/core';
import { Item } from '../models/item/item';
import { ServerService } from '../services/server/server.service';
import { AuthService } from '../services/auth/auth.service';
import { DataService } from '../services/data/data.service';
import { catchError, map, tap } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private server: ServerService, public auth: AuthService, public data: DataService) {
    auth.handleAuthentication();
    // server.getToken();
  }

  ngOnInit() {
    // Current Location
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.data.myLatitude = position.coords.latitude;
        this.data.myLongitude = position.coords.longitude;

        this.server.getItems().subscribe();
      });
    }
  }
}
