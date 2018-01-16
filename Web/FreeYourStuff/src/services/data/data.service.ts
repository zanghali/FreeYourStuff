import { Injectable } from '@angular/core';
import { User } from '../../models/user/user';
import { Item, Category } from '../../models/item/item';

@Injectable()
export class DataService {
  SERVER_URL = "http://freeyourstuff.ddns.net:3000/";

  items: Item[];
  myLatitude: number;
  myLongitude: number;

  constructor() { }

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
      date: null
    }
    return user;
  }

  public getCategoryIcon(category) {
    var cat;

    switch (<any>Category[category]) {
      case Category.animal:
        cat = "pets";
        break;
      case Category.game:
        cat = "videogame_asset";
        break;
      case Category.furniture:
        cat = "weekend";
        break;
      case Category.music:
        cat = "music_note";
        break;
      case Category.multimedia:
        cat = "video_library";
        break;
      case Category.food:
        cat = "restaurant";
        break;
      case Category.sport:
        cat = "directions_bike";
        break;
      case Category.book:
        cat = 'import_contacts';
        break;
      case Category.clothing:
        cat = 'shopping_basket';
        break;
      case Category.other:
        cat = 'flag';
        break;
    }

    return cat;
  }

  public getImgPath(path): string {
    return this.SERVER_URL + "assets" + path;
  }
}
