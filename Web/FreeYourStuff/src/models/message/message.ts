import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

import { User } from '../user/user';

@Injectable()
export class Message {
    id_item: string;
    id_sender: string;
    id_receiver: string;
    date: Date;
    message: string;
}