import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

import { Item } from '../item/item';

@Injectable()
export class User {
    id: string;
    firstname: string;
    lastname: string;
    nickname: string;
    phone: string;
    email: string;
    address: string;    
    photo: string;
    date: Date
}
