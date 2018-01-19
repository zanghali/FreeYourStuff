import { Injectable } from '@angular/core';
import { User } from '../user/user';

export enum Category {
    none,
    furniture,
    multimedia,
    clothing,
    sport,
    food,
    game,
    tool,
    hygiene,
    music,
    animal,
    book,
    nature,
    service,
    other
}

export enum Status {
    none,

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
    id_item: string;
    category: Category;
    title: string;
    description: string;
    photo: string;
    address: string;
    phone: number;
    status: Status;
    creation_date: Date;
    gps: string;
    availability: Availability;
    id_user: string;
    distance: string;
}
