import { Injectable } from '@angular/core';
import { User } from '../../models/user/user';
import { Item, Category, Availability, Status } from '../../models/item/item';

@Injectable()
export class DataService {
  SERVER_URL = "http://freeyourstuff.ddns.net:3000/";

  // Items shared data

  items: Item[] = [];
  myLatitude: number;
  myLongitude: number;
  filters = {
    'distance': 5,
    'category': 'Toutes',
    'availability': 'Toutes'
  };
  search: String = "";
  offers: Item[] = [];
  demands: Item[] = [];

  // User shared data

  user: User = {
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

  constructor() { }

  // Data helpers

  public getOffersByStatus(status): Item[] {
    return this.offers.filter(item => {
      return (Status[item.status] == status);
    });
  }

  public getDemandsByStatus(status): Item[] {
    return this.demands.filter(item => {
      return (Status[item.status] == status);
    });
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

  public capitalize(input) {
    return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
  }

  public availabilityToEnum(input) {
    switch (input) {
      case "Dès aujourd'hui":
        return Availability[Availability.asap];
      case "Dans les jours à venir":
        return Availability[Availability.upcomingDays];
      case "Dans les semaines à venir":
        return Availability[Availability.upcomingWeeks];
    }
  }

  public enumToAvailability(input) {
    switch (input) {
      case 'asap':
        return "Dès aujourd'hui";
      case 'upcomingDays':
        return "Dans les jours à venir";
      case 'upcomingWeeks':
        return "Dans les semaines à venir";
    }
  }

  public formatDistance(input): string {
    if (input >= 1000) {
      return (input / 1000).toFixed(0) + ' km';
    } else {
      return input + ' m';
    }
  }
}
