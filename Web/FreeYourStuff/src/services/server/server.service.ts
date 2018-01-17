import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import { of } from 'rxjs/observable/of';
import { Item, Category, Status, Availability } from '../../models/item/item';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable()
export class ServerService {
  SERVER_URL = "http://freeyourstuff.ddns.net:3000/";
  httpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
     })
  };

  constructor(public http: HttpClient) { }

  // User

  addUser(lastname, firstname, email, callback) {
    let details = { 'lastname': lastname, 'firstname': firstname, 'email': email };

    this.http.post(this.SERVER_URL + "addUser", details, this.httpOptions)
      .subscribe(data => {
        callback(null, data);
      }, error => {
        callback(error, null);
      });
  }

  getUserByEmail(email, callback) {
    let details = { 'email': email };

    this.http.post(this.SERVER_URL + "getUserByEmail", details, this.httpOptions)
      .subscribe(data => {
        callback(null, data);
      }, error => {
        callback(error, null);
      });
  }

  setUserInterestedByItem(idUser, idItem, callback) {
    let details = { 'id_user': idUser, 'id_item': idItem };

    this.http.post(this.SERVER_URL + "setUserInterestedByItem", details, this.httpOptions)
      .subscribe(data => {
        callback(null, data);
      }, error => {
        callback(error, null);
      });
  }

  getUserInterestedByItem(idItem, callback) {
    let details = { 'id_item': idItem };

    this.http.post(this.SERVER_URL + "getUserInterestedByItem", details, this.httpOptions)
      .subscribe(data => {
        callback(null, data);
      }, error => {
        callback(error, null);
      });
  }

  // Item

  addItem(item: Item): Observable<Item> {
    return this.http.post<Item>(this.SERVER_URL + "addItem", item, this.httpOptions)
    .pipe(
      tap(item => console.log('addItem'))
    );
  }
  
  deleteItem(item: Item, id_user): Observable<Item> {
    let details = {
      'id_item': item.id_item,
      'photo': item.photo,
      'id_user': id_user
    };

    return this.http.post<Item>(this.SERVER_URL + "deleteItem", details, this.httpOptions)
    .pipe(
      tap(item => console.log('deleteItem'))
    );
  }

  getItems(latitude, longitude, distance): Observable<Item[]> {
    let details = {
      'gps': latitude + ',' + longitude,
      'distance': distance
    };

    return this.http.post<Item[]>(this.SERVER_URL + "getItemByFilterGeo", details, this.httpOptions)
    .pipe(
      tap(items => console.log('getItems'))
    );
  }

  // Gets all items we've added
  getItemsByUser(id_user): Observable<Item[]> {
    let details = {
      'id_user': id_user
    };

    return this.http.post<Item[]>(this.SERVER_URL + "getItemByUser", details, this.httpOptions)
    .pipe(
      tap(items => console.log('getItemsByUser'))
    );
  }

  // Gets all demands for our items
  getItemOfUserInterestedBy(id_user): Observable<Item[]> {
    let details = {
      'id_user': id_user
    };

    return this.http.post<Item[]>(this.SERVER_URL + "getItemOfUserInterestedBy", details, this.httpOptions)
    .pipe(
      tap(items => console.log('getItemOfUserInterestedBy'))
    );
  }
}
