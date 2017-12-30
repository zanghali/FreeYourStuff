import { Injectable } from '@angular/core';
import { Item, Category, Status, Availability } from '../../models/item/item';

@Injectable()
export class ServerService {

  constructor() { }

  createItem(json): boolean {
    console.log(json); // Send Json
    
    return true;
  }

  getItems(): Item[] {
    return [
      {
        category: Category.animal,
        title: 'chien 1',
        description: 'A donner 1',
        photo: 'http://s1.1zoom.me/big0/674/430891-Kysb.jpg',
        address: "9 Rue d'Inkermann, Villeurbanne",
        phone: 0,
        status: Status.waiting,
        creationDate: new Date(),
        location: '1.0',
        availability: Availability.asap,
        user: null
      },
      {
        category: Category.sport,
        title: 'velo',
        description: 'A donner 2',
        photo: 'http://www.cycloland40.fr/wp-content/uploads/2015/07/velo3.png',
        address: "18 Rue de Genève, Lyon",
        phone: 0,
        status: Status.waiting,
        creationDate: new Date(),
        location: '1.3',
        availability: Availability.asap,
        user: null
      },
      {
        category: Category.game,
        title: 'monopoly',
        description: 'A donner 2',
        photo: 'https://www.newstatesman.com/sites/default/files/styles/nodeimage/public/blogs_2017/03/gettyimages-85991649.jpg?itok=ncmDUvKe',
        address: '81 Avenue Galline, 69100 Villeurbanne',
        phone: 0,
        status: Status.waiting,
        creationDate: new Date(),
        location: '1.3',
        availability: Availability.asap,
        user: null
      },
      {
        category: Category.food,
        title: 'Chips',
        description: 'A donner 2',
        photo: 'https://www.newstatesman.com/sites/default/files/styles/nodeimage/public/pringles.jpg?itok=x6VlZn0J',
        address: '18-20 Rue Gervais Bussière, 69100 Villeurbanne',
        phone: 0,
        status: Status.waiting,
        creationDate: new Date(),
        location: '1.3',
        availability: Availability.asap,
        user: null
      },
      {
        category: Category.clothing,
        title: 'chaussures',
        description: 'A donner 2',
        photo: 'http://joggbox.fr/jogg-blog/wp-content/uploads/2015/01/chaussures.jpg',
        address: '37-33 Rue Louis Guérin, 69100 Villeurbanne',
        phone: 0,
        status: Status.waiting,
        creationDate: new Date(),
        location: '1.3',
        availability: Availability.asap,
        user: null
      },
      {
        category: Category.game,
        title: 'Jeu ps4',
        description: 'A donner 2',
        photo: 'http://www.consolefun.fr/upload/images/1472465123maxresdefault.jpg',
        address: '42-50 Rue Masséna, 69006 Lyon ',
        phone: 0,
        status: Status.waiting,
        creationDate: new Date(),
        location: '1.3',
        availability: Availability.asap,
        user: null
      },
      {
        category: Category.animal,
        title: 'chien 1',
        description: 'A donner 1',
        photo: 'http://s1.1zoom.me/big0/674/430891-Kysb.jpg',
        address: "9 Rue d'Inkermann, Villeurbanne",
        phone: 0,
        status: Status.waiting,
        creationDate: new Date(),
        location: '1.0',
        availability: Availability.asap,
        user: null
      },
      {
        category: Category.sport,
        title: 'velo',
        description: 'A donner 2',
        photo: 'http://www.cycloland40.fr/wp-content/uploads/2015/07/velo3.png',
        address: "18 Rue de Genève, Lyon",
        phone: 0,
        status: Status.waiting,
        creationDate: new Date(),
        location: '1.3',
        availability: Availability.asap,
        user: null
      }
    ];
  }

}
