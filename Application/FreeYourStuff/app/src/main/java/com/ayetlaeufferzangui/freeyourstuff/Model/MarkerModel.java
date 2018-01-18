package com.ayetlaeufferzangui.freeyourstuff.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by lothairelaeuffer on 22/12/2017.
 */

public class MarkerModel implements ClusterItem{

    private String id_item;
    private LatLng position;
    private String title;
    private int categoryIconUrl;

    public MarkerModel(LatLng position, String title, int categoryIconUrl) {
        this.position = position;
        this.title = title;
        this.categoryIconUrl = categoryIconUrl;
    }

    public MarkerModel(String id_item, LatLng position, String title, int categoryIconUrl) {
        this.id_item = id_item;
        this.position = position;
        this.title = title;
        this.categoryIconUrl = categoryIconUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getCategoryIconUrl() {
        return categoryIconUrl;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public String getId_item() {
        return id_item;
    }
}
