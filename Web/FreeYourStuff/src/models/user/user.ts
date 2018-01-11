import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

import { Item } from '../item/item';

@Injectable()
export class User {
    firstname: string;
    lastname: string;
    nickname: string;
    phone: string;
    email: string;
    address: string;
    photo: string;
    items: Item[];
}
