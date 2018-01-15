package com.ayetlaeufferzangui.freeyourstuff.Model;

import java.io.Serializable;

/**
 * Created by lothairelaeuffer on 08/01/2018.
 */

public class Item implements Serializable{

    private String category;
    private String title;
    private String description;
    private String photo;
    private String address;
    private String phone;
    private String status;
    private String gps;
    private String availability;
    private String id_user;

    private String id_item;

    public Item(String category, String title, String description, String photo, String address, String phone, String status, String gps, String availability, String id_user) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.gps = gps;
        this.availability = availability;
        this.id_user = id_user;
    }

    public Item(String category, String title, String description, String photo, String address, String phone, String status, String gps, String availability, String id_user, String id_item) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.gps = gps;
        this.availability = availability;
        this.id_user = id_user;
        this.id_item = id_item;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", gps='" + gps + '\'' +
                ", availability='" + availability + '\'' +
                ", id_user='" + id_user + '\'' +
                ", id_item='" + id_item + '\'' +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public String getGps() {
        return gps;
    }

    public String getAvailability() {
        return availability;
    }

    public String getId_user() {
        return id_user;
    }

    public String getId_item() {
        return id_item;
    }
}
