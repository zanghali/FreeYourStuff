import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
// Material
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule, MatCardModule, MatCheckboxModule, MatDialogModule, MatFormFieldModule, MatGridListModule, MatIconModule, MatInputModule, MatListModule, MatMenuModule, MatOptionModule, MatSelectModule, MatToolbarModule } from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AgmCoreModule } from '@agm/core';
// Components
import { AppComponent } from './app.component';
import { MenuComponent } from '../components/menu/menu.component';
import { ItemComponent } from '../components/item/item.component';
import { ItemListComponent } from '../components/item-list/item-list.component';
import { MapComponent } from '../components/map/map.component';
import { MarkerComponent } from '../components/marker/marker.component';
import { ItemCreationDialogComponent } from '../components/item-creation-dialog/item-creation-dialog.component';
// Services
import { ServerService } from '../services/server/server.service';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    ItemComponent,
    ItemListComponent,
    MapComponent,
    MarkerComponent,
    ItemCreationDialogComponent,
  ],
  entryComponents: [
    ItemCreationDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatDialogModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatOptionModule,
    MatSelectModule,
    MatToolbarModule,
    FormsModule,
    ReactiveFormsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCPW_yYAkgKnH3BVbRWfY5CStD2WqRef_o'
    })
  ],
  providers: [ServerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
