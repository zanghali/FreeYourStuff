package com.ayetlaeufferzangui.freeyourstuff.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lothairelaeuffer on 15/01/2018.
 */

public class ItemShortList {

    private int categoryIconUrl;
    private String title;
    private String id_item;
    private String creation_date;
    private String id_user;

    public ItemShortList(int categoryIconUrl, String title, String id_item) {
        this.categoryIconUrl = categoryIconUrl;
        this.title = title;
        this.id_item = id_item;
    }

    public ItemShortList(int categoryIconUrl, String title) {
        this.categoryIconUrl = categoryIconUrl;
        this.title = title;
    }

    public ItemShortList(int categoryIconUrl, String title, String id_item, String creation_date, String id_user) {
        this.categoryIconUrl = categoryIconUrl;
        this.title = title;
        this.id_item = id_item;
        this.creation_date = creation_date;
        this.id_user = id_user;
    }

    public int getCategoryIconUrl() {
        return categoryIconUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getId_item() {
        return id_item;
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

    public String getId_user() {
        return id_user;
    }
}

