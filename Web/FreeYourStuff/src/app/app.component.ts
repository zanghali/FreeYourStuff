import { Component } from '@angular/core';
import { Item } from '../models/item/item';
import { ServerService } from '../services/server/server.service';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  items: Item[];

  constructor(private serverService: ServerService, public auth: AuthService) {
    auth.handleAuthentication();
  }

  ngOnInit() {
    this.items = this.serverService.getItems();
  }
}
