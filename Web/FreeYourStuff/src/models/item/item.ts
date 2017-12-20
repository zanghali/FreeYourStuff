import { Injectable } from '@angular/core';
import { User } from '../user/user';

export enum Category {
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

export enum Status {
    waiting,
    inProgress,
    done,
    aborted
}

export enum Availability {
    asap,
    upcomingDays,
    upcomingWeeks
}

export class Item {
    category: Category;
    title: string;
    description: string;
    photo: string;
    address: string;
    phone: number;
    status: Status;
    creationDate: Date;
    location: string;
    availability: Availability;
    user: User;
}
