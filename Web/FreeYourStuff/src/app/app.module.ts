import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
// Material
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatCardModule, MatCheckboxModule, MatFormFieldModule, MatGridListModule, MatIconModule, MatListModule, MatMenuModule, MatToolbarModule} from '@angular/material';
import { AgmCoreModule } from '@agm/core';

import { AppComponent } from './app.component';
import { MenuComponent } from '../components/menu/menu.component';
import { ItemComponent } from '../components/item/item.component';
import { ItemListComponent } from '../components/item-list/item-list.component';
import { MapComponent } from '../components/map/map.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    ItemComponent,
    ItemListComponent,
    MapComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatToolbarModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCPW_yYAkgKnH3BVbRWfY5CStD2WqRef_o'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
