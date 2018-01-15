package com.ayetlaeufferzangui.freeyourstuff.Model;

/**
 * Created by lothairelaeuffer on 15/01/2018.
 */

public class ItemShortList {

    private int categoryIconUrl;
    private String title;

    public ItemShortList(int categoryIconUrl, String title) {
        this.categoryIconUrl = categoryIconUrl;
        this.title = title;
    }

    public int getCategoryIconUrl() {
        return categoryIconUrl;
    }

    public String getTitle() {
        return title;
    }

}

