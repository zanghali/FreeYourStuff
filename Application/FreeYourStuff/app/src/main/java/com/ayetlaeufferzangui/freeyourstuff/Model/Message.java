package com.ayetlaeufferzangui.freeyourstuff.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lothairelaeuffer on 23/01/2018.
 */

public class Message {

    private String message;
    private String date;
    private String id_item;
    private String id_sender;
    private String id_receiver;
    private String id_chat;

    public Message(String message, String date, String id_item, String id_sender, String id_receiver, String id_chat) {
        this.message = message;
        this.date = date;
        this.id_item = id_item;
        this.id_sender = id_sender;
        this.id_receiver = id_receiver;
        this.id_chat = id_chat;
    }

    public Message(String message, String id_item, String id_sender, String id_receiver) {
        this.message = message;
        this.id_item = id_item;
        this.id_sender = id_sender;
        this.id_receiver = id_receiver;
    }

    public String getMessage() {
        return message;
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

    public String getId_sender() {
        return id_sender;
    }

    public String getId_receiver() {
        return id_receiver;
    }

    public String getId_chat() {
        return id_chat;
    }
}
