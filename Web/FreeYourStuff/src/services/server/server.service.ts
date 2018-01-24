import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Item, Category, Status, Availability } from '../../models/item/item';
import { catchError, map, tap } from 'rxjs/operators';
import { DataService } from '../data/data.service';
import { User } from '../../models/user/user';
import { Message } from '../../models/message/message';

@Injectable()
export class ServerService {
  SERVER_URL = "http://freeyourstuff.ddns.net:3000/";
  httpOptions = {
    headers: new HttpHeaders({
      // Authorization: this.data.apiToken,
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    })
  };

  constructor(public http: HttpClient, public data: DataService) { }

  // Auth0 jwt token

  // getToken() {
  //   let httpOptions = {
  //     headers: new HttpHeaders({
  //       "content-type": "application/json"
  //     })
  //   };

  //   let details = {
  //     "client_id": "gAesbsE0QTokB59tHan3qyMr6IROFJnf",
  //     "client_secret": "Gt2iVVCtKGF7whkjHV9I4zmJW56B743BLqT_5AEYBDri2Aarb7PTEiyxgdJubgmt",
  //     "audience": "http://freeyourstuff.ddns.net:3000/",
  //     "grant_type": "client_credentials"
  //   };

  //   this.http.post('https://freeyourstuff.eu.auth0.com/oauth/token', details, httpOptions)
  //     .subscribe(data => {
  //       this.data.apiToken = data["token_type"] + ' ' + data["access_token"];
  //     }, error => {
  //       console.log(error);
  //     });
  // }

  // User requests

  addUser(lastname, firstname, email, photo, callback) {
    let details = {
      'lastname': lastname,
      'firstname': firstname,
      'email': email,
      'photo': photo
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

  getUserById(id_user, callback) {
    let details = { 'id_user': id_user };

    this.http.post(this.SERVER_URL + "getUserById", details, this.httpOptions)
      .subscribe(data => {
        callback(null, data);
      }, error => {
        callback(error, null);
      });
  }

  setUserInterestedByItem(idItem, callback) {
    let details = {
      'id_user': this.data.getUser().id_user,
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
      'firstname': this.data.getUser().firstname,
      'lastname': this.data.getUser().lastname,
      'email': this.data.getUser().email,
      'photo': this.data.getUser().photo
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
      'id_user': this.data.getUser().id_user,
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
      'id_user': this.data.getUser().id_user
    };

    return this.http.post<Item>(this.SERVER_URL + "deleteItem", details, this.httpOptions)
      .pipe(
      tap(item => {
        this.getItems().subscribe();
        this.getItemsByUser().subscribe();
      })
      );
  }

  updateItemStatus(id_item, id_userInterestedBy): Observable<Item> {
    let details = {
      'id_item': id_item,
      'id_userInterestedBy': id_userInterestedBy,
      'id_user': this.data.getUser().id_user
    };

    return this.http.post<Item>(this.SERVER_URL + "updateItemStatus", details, this.httpOptions)
      .pipe(
      tap(item => {
        this.getItems().subscribe();
        this.getItemsByUser().subscribe();
        this.getItemOfUserInterestedBy().subscribe();
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
      'id_user': this.data.getUser().id_user
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
      'id_user': this.data.getUser().id_user
    };

    return this.http.post<Item[]>(this.SERVER_URL + "getItemOfUserInterestedBy", details, this.httpOptions)
      .pipe(
      tap(items => this.data.demands = items)
      );
  }

  // Chat requests

  getChat(id_item, id_person): Observable<Message[]> {
    let details = {
      'id_item': id_item,
      'first_person': id_person,
      'second_person': this.data.getUser().id_user
    };

    return this.http.post<Message[]>(this.SERVER_URL + "getChat", details, this.httpOptions);
  }

  addChat(message: Message): Observable<Message> {
    return this.http.post<Message>(this.SERVER_URL + "addChat", message, this.httpOptions);
  }
}
