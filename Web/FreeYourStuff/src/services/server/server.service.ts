import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Item, Category, Status, Availability } from '../../models/item/item';
import { catchError, map, tap } from 'rxjs/operators';
import { DataService } from '../data/data.service';
import { User } from '../../models/user/user';

@Injectable()
export class ServerService {
  SERVER_URL = "http://freeyourstuff.ddns.net:3000/";
  httpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    })
  };

  constructor(public http: HttpClient, public data: DataService) { }

  // User requests

  addUser(lastname, firstname, email, callback) {
    let details = {
      'lastname': lastname,
      'firstname': firstname,
      'email': email
    };

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

  setUserInterestedByItem(idItem, callback) {
    let details = {
      'id_user': this.data.user.id,
      'id_item': idItem
    };

    this.http.post(this.SERVER_URL + "setUserInterestedByItem", details, this.httpOptions)
      .subscribe(data => {
        callback(null, data);
      }, error => {
        callback(error, null);
      });
  }

  updateUser(callback) {
    let details = {
      'firstname': this.data.user.firstname,
      'lastname': this.data.user.lastname,
      'email': this.data.user.email
    };

    this.http.post(this.SERVER_URL + "updateUser", details, this.httpOptions)
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

  getNumberInterestedByItem(idItem, callback) {
    let details = { 'id_item': idItem };

    this.http.post(this.SERVER_URL + "getNumberInterestedByItem", details, this.httpOptions)
      .subscribe(data => {
        callback(null, data);
      }, error => {
        callback(error, null);
      });
  }

  deleteUserInterestedByItem(id_item): Observable<User> {
    let details = { 
      'id_user': this.data.user.id,
      'id_item': id_item
     };

    return this.http.post<User>(this.SERVER_URL + "deleteUserInterestedByItem", details, this.httpOptions)
      .pipe(
        tap(user => this.getItemOfUserInterestedBy().subscribe())
      );
  }

  // Item requests

  addItem(item: Item): Observable<Item> {
    return this.http.post<Item>(this.SERVER_URL + "addItem", item, this.httpOptions)
      .pipe(
        tap(item => this.getItems().subscribe())
      );
  }

  deleteItem(item: Item): Observable<Item> {
    let details = {
      'id_item': item.id_item,
      'photo': item.photo,
      'id_user': this.data.user.id
    };

    return this.http.post<Item>(this.SERVER_URL + "deleteItem", details, this.httpOptions)
      .pipe(
        tap(item => {
          this.getItems().subscribe();
          this.getItemsByUser().subscribe();
        })
      );
  }

  getItems(): Observable<Item[]> {
    let details = {
      'gps': this.data.myLatitude + ',' + this.data.myLongitude,
      'distance': +this.data.filters.distance * 1000,
      'keywords': this.data.search
    };

    return this.http.post<Item[]>(this.SERVER_URL + "getItemByKeywords", details, this.httpOptions)
      .pipe(
        tap(items => this.data.items = items.filter(item => {
          let category = (this.data.filters.category != 'Toutes') ? (item.category.toString() == this.data.filters.category.toLowerCase()) : true;
          let availability = (this.data.filters.availability != 'Toutes') ? (item.availability.toString() == this.data.availabilityToEnum(this.data.filters.availability)) : true;

          return (category && availability);
        }))
      );
  }

  // Gets all offers we've added
  getItemsByUser(): Observable<Item[]> {
    let details = {
      'gps': this.data.myLatitude + ',' + this.data.myLongitude,
      'id_user': this.data.user.id
    };

    return this.http.post<Item[]>(this.SERVER_URL + "getItemByUser", details, this.httpOptions)
      .pipe(
        tap(items => this.data.offers = items)
      );
  }

  // Gets all demands for our items
  getItemOfUserInterestedBy(): Observable<Item[]> {
    let details = {
      'gps': this.data.myLatitude + ',' + this.data.myLongitude,
      'id_user': this.data.user.id
    };

    return this.http.post<Item[]>(this.SERVER_URL + "getItemOfUserInterestedBy", details, this.httpOptions)
      .pipe(
        tap(items => this.data.demands = items)
      );
  }
}
