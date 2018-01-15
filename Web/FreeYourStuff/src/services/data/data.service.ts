import { Injectable } from '@angular/core';
import { User } from '../../models/user/user';
import { ServerService } from '../server/server.service';
import { Item } from '../../models/item/item';

@Injectable()
export class DataService {
  items: Item[];
  myLatitude: number;
  myLongitude: number;

  constructor(public server: ServerService) { }

  public getUser(): User {
    var user: User = {
      id: localStorage.getItem("id"),
      firstname: localStorage.getItem("firstname"),
      lastname: localStorage.getItem("lastname"),
      nickname: localStorage.getItem("nickname"),
      phone: localStorage.getItem("phone"),
      email: localStorage.getItem("email"),
      address: localStorage.getItem("address"),
      photo: localStorage.getItem("photo"),
      items: null
    }
    return user;
  }
}
