import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

import { Item } from '../item/item';

@Injectable()
export class User {
    private firstname: string;
    private lastname: string;
    private nickname: string;
    private phone: number;
    private email: string;
    private address: string;
    private photo: string;
    private items: Array<Item>;

    constructor(data) {
        this.firstname = data.firstname;
        this.lastname = data.lastname;
        this.nickname = data.nickname;
        this.phone = data.phone;
        this.email = data.email;
        this.address = data.address;
        this.photo = data.photo;
        this.items = [];
    }
    
    public getFirstname(): string {
        return this.firstname;
    }
    public setFirstname(firstname: string): void {
        this.firstname = firstname;
    }

    public getLastname(): string {
        return this.lastname;
    }
    public setLastname(lastname: string): void {
        this.lastname = lastname;
    }

    public getNickname(): string {
        return this.nickname;
    }
    public setNickname(nickname: string): void {
        this.nickname = nickname;
    }

    public getPhone(): number {
        return this.phone;
    }
    public setPhone(phone: number): void {
        this.phone = phone;
    }

    public getEmail(): string {
        return this.email;
    }
    public setEmail(email: string): void {
        this.email = email;
    }
    
    public getAddress(): string {
        return this.address;
    }
    public setAddress(address: string): void {
        this.address = address;
    }
    
    public getPhoto(): string {
        return this.photo;
    }
    public setPhoto(photo: string): void {
        this.photo = photo;
    }
    
    public getItems(): Array<Item> {
        return this.items;
    }
    public setItems(items: Array<Item>): void {
        this.items = items;
    }
}
