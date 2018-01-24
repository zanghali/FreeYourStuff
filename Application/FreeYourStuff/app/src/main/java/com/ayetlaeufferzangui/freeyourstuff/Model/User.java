package com.ayetlaeufferzangui.freeyourstuff.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lothairelaeuffer on 10/01/2018.
 */

public class User {

    private String lastname;
    private String firstname;
    private String email;
    private String id_user;
    private String photo;
    private String date;
    private String id_item;

    public User(String lastname, String firstname, String email, String photo) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.photo = photo;
    }

    public User(String lastname, String firstname, String email, String id_user, String photo) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.id_user = id_user;
        this.photo = photo;
    }

    public User(String lastname, String firstname, String email, String id_user, String date, String photo) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.id_user = id_user;
        this.date = date;
        this.photo = photo;
    }

    public User(String email) {
        this.email = email;
    }



    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getId_user() {
        return id_user;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDate() throws ParseException {
        String[] parts = date.split("-");
        String year = parts[0];
        String month = parts[1];
        String day = parts[2].split("T")[0];

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date creation_date = format.parse(day + "/" + month + "/" + year);


        SimpleDateFormat displayFormat = new SimpleDateFormat("MMM d, yyyy");
        String creation_dateString = displayFormat.format(creation_date);
        return creation_dateString;
    }

    public String getId_item() {
        return id_item;
    }

    @Override
    public String toString() {
        return "User{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", id_user='" + id_user + '\'' +
                ", photoURL='" + photo + '\'' +
                ", date='" + date + '\'' +
                ", id_item='" + id_item + '\'' +
                '}';
    }
}
