package com.ayetlaeufferzangui.freeyourstuff.Model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private String distance;
    private String creation_date;


    public Item(String category, String title, String description, String address, String phone, String availability, String id_user) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.availability = availability;
        this.id_user = id_user;
    }

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

    public Item(String category, String title, String description, String photo, String address, String phone, String status, String gps, String availability, String id_user, String id_item, String distance) {
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
        this.distance = distance;
    }

    public Item(String category, String title, String description, String photo, String address, String phone, String status, String gps, String availability, String id_user, String id_item, String distance, String creation_date) {
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
        this.distance = distance;
        this.creation_date = creation_date;
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
                ", distance='" + distance + '\'' +
                ", creation_date='" + creation_date + '\'' +
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

    public String getDistance() {
        return distance;
    }

    public String getCreation_date() throws ParseException {

        String[] parts = creation_date.split("-");
        String year = parts[0];
        String month = parts[1];
        String day = parts[2].split("T")[0];

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date creation_date = format.parse(day + "/" + month + "/" + year);


        SimpleDateFormat displayFormat = new SimpleDateFormat("MMM d, yyyy");
        String creation_dateString = displayFormat.format(creation_date);
        return creation_dateString;
    }
}
