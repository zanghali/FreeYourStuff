import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

import { User } from '../user/user';

@Injectable()
export class Message {
    private sender: User;
    private date: Date;
    private content: string;

    constructor(data) {
        this.sender = data.sender;
        this.date = data.date;
        this.content = data.content;
    }
    
    public getSender(): User {
        return this.sender;
    }
    public setSender(sender: User): void {
        this.sender = sender;
    }

    public getDate(): Date {
        return this.date;
    }
    public setDate(date: Date): void {
        this.date = date;
    }

    public getContent(): string {
        return this.content;
    }
    public setContent(content: string): void {
        this.content = content;
    }
}
