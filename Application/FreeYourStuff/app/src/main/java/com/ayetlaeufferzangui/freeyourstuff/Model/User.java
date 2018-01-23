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
    private String photoURL;
    private String date;

    public User(String lastname, String firstname, String email) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
    }

    public User(String lastname, String firstname, String email, String id_user, String date) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.id_user = id_user;
        this.date = date;
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

    public String getPhotoURL() {
        return photoURL;
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

    @Override
    public String toString() {
        return "User{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", id_user='" + id_user + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
