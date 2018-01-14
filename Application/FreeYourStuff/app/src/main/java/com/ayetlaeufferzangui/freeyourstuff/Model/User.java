package com.ayetlaeufferzangui.freeyourstuff.Model;

/**
 * Created by lothairelaeuffer on 10/01/2018.
 */

public class User {

    private String lastname;
    private String firstname;
    private String email;
    private String id_user;

    public User(String lastname, String firstname, String email) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
    }

    public User(String lastname, String firstname, String email, String id_user) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.id_user = id_user;
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

    @Override
    public String toString() {
        return "User{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", id_user='" + id_user + '\'' +
                '}';
    }
}
