import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FileDropDirective, FileSelectDirective } from 'ng2-file-upload';
// Material
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule, MatCardModule, MatCheckboxModule, MatDialogModule, MatFormFieldModule, MatGridListModule, MatIconModule, MatInputModule, MatListModule, MatMenuModule, MatOptionModule, MatSelectModule, MatSnackBarModule, MatToolbarModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AgmCoreModule } from '@agm/core';
import { AgmJsMarkerClustererModule } from '@agm/js-marker-clusterer';
// Components
import { AppComponent } from './app.component';
import { MenuComponent } from '../components/menu/menu.component';
import { ItemComponent } from '../components/item/item.component';
import { ItemListComponent } from '../components/item-list/item-list.component';
import { MapComponent } from '../components/map/map.component';
import { MarkerComponent } from '../components/marker/marker.component';
import { ItemCreationDialogComponent } from '../components/item-creation-dialog/item-creation-dialog.component';
import { ItemDialogComponent } from '../components/item-dialog/item-dialog.component';
import { MenuAuthComponent } from '../components/menu-auth/menu-auth.component';
import { ProfileDialogComponent } from '../components/profile-dialog/profile-dialog.component';
// Services
import { ServerService } from '../services/server/server.service';
import { AuthService } from '../services/auth/auth.service';
import { DataService } from '../services/data/data.service';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    ItemComponent,
    ItemListComponent,
    MapComponent,
    MarkerComponent,
    ItemCreationDialogComponent,
    ItemDialogComponent,
    MenuAuthComponent,
    ProfileDialogComponent,
    FileDropDirective,
    FileSelectDirective
  ],
  entryComponents: [
    ItemCreationDialogComponent,
    ProfileDialogComponent,
    ItemDialogComponent
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
    MatSnackBarModule,
    MatToolbarModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCPW_yYAkgKnH3BVbRWfY5CStD2WqRef_o'
    }),
    AgmJsMarkerClustererModule
  ],
  providers: [
    ServerService,
    AuthService,
    DataService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
