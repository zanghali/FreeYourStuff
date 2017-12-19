import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

import { Item } from '../item/item';
import { User } from '../user/user';
import { Message } from '../message/message';

@Injectable()
export class Conversation {
    private item: Item;
    private interlocutor: User;
    private messages: Array<Message>;

    constructor(data) {
        this.item = data.item;
        this.interlocutor = data.interlocutor;
        this.messages = data.messages;
    }
    
    public getItem(): Item {
        return this.item;
    }
    public setItem(item: Item): void {
        this.item = item;
    }

    public getInterlocutor(): User {
        return this.interlocutor;
    }
    public setInterlocutor(interlocutor: User): void {
        this.interlocutor = interlocutor;
    }

    public getMessages(): Array<Message> {
        return this.messages;
    }
    public setMessages(messages: Array<Message>): void {
        this.messages = messages;
    }
}
