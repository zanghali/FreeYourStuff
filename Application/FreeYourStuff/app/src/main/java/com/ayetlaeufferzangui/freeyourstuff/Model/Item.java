package com.ayetlaeufferzangui.freeyourstuff.Model;

/**
 * Created by lothairelaeuffer on 08/01/2018.
 */

public class Item {

    private String category;
    private String title;
    private String description;
    private String photo;
    private String address;
    private String phone;
    private String status;
    private String gps;
    private String availability;
    private String idUser;

    public Item(String category, String title, String description, String photo, String address, String phone, String status, String gps, String availability, String idUser) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.gps = gps;
        this.availability = availability;
        this.idUser = idUser;
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
                ", idUser='" + idUser + '\'' +
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

    public String getIdUser() {
        return idUser;
    }
}