import { Component } from '@angular/core';
import { Item } from '../models/item/item';
import { ServerService } from '../services/server/server.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  items: Item[];

  constructor(private serverService: ServerService) { }

  ngOnInit() {
    this.items = this.serverService.getItems();
  }
}
