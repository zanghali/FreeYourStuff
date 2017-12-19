import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

import { User } from '../user/user';

enum Category {
    furniture,
    multimedia,
    clothing,
    sport,
    food,
    game,
    tool,
    hygiene,
    music,
    animals,
    animal,
    book,
    nature,
    service,
    other
}

enum Status {
    waiting,
    inProgress,
    done,
    aborted
}

enum Availability {
    asap,
    upcomingDays,
    upcomingWeeks
}

@Injectable()
export class Item {
    private category: Category;
    private title: string;
    private description: string;
    private photo: string;
    private address: string;
    private phone: number;
    private status: Status;
    private creationDate: Date;
    private location: string;
    private availability: Availability;
    private user: User;

    constructor(data) {
        this.category = data.category;
        this.title = data.title;
        this.description = data.description;
        this.photo = data.photo;
        this.address = data.address;
        this.phone = data.phone;
        this.status = data.status;
        this.creationDate = data.creationDate;
        this.location = data.location;
        this.availability = data.availability;
        this.user = data.user;
    }

    public getCategory(): Category {
        return this.category;
    }
    public setCategory(category: Category): void {
        this.category = category;
    }

    public getTitle(): string {
        return this.title;
    }
    public setTitle(title: string): void {
        this.title = title;
    }

    public getDescription(): string {
        return this.description;
    }
    public setDescription(description: string): void {
        this.description = description;
    }
    
    public getPhoto(): string {
        return this.photo;
    }
    public setPhoto(photo: string): void {
        this.photo = photo;
    }

    public getAddress(): string {
        return this.address;
    }
    public setAddress(address: string): void {
        this.address = address;
    }

    public getPhone(): number {
        return this.phone;
    }
    public setPhone(phone: number): void {
        this.phone = phone;
    }

    public getStatus(): Status {
        return this.status;
    }
    public setStatus(status: Status): void {
        this.status = status;
    }
    
    public getCreationDate(): Date {
        return this.creationDate;
    }
    public setCreationDate(creationDate: Date): void {
        this.creationDate = creationDate;
    }
    
    public getLocation(): string {
        return this.location;
    }
    public setLocation(location: string): void {
        this.location = location;
    }
    
    public getAvailability(): Availability {
        return this.availability;
    }
    public setAvailability(availability: Availability): void {
        this.availability = availability;
    }
    
    public getUser(): User {
        return this.user;
    }
    public setUser(user: User): void {
        this.user = user;
    }
}
