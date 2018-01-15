package com.ayetlaeufferzangui.freeyourstuff.Model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by lothairelaeuffer on 22/12/2017.
 */

public class MarkerModel {

    private LatLng latLng;
    private String title;
    private int categoryIconUrl;

    public MarkerModel(LatLng latLng, String title, int categoryIconUrl) {
        this.latLng = latLng;
        this.title = title;
        this.categoryIconUrl = categoryIconUrl;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getTitle() {
        return title;
    }

    public int getCategoryIconUrl() {
        return categoryIconUrl;
    }
}
