package com.ayetlaeufferzangui.freeyourstuff.Model;

import com.ayetlaeufferzangui.freeyourstuff.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by lothairelaeuffer on 22/12/2017.
 */

public class MarkerModel {

    private LatLng latLng;
    private String title;
    private int icon;

    public MarkerModel(LatLng latLng, String title, Category category ) {
        this.latLng = latLng;
        this.title = title;
        this.icon =  createIconUrl(category);
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public int createIconUrl(Category category) {

        int cat =0;

        switch (category) {
            case furniture:
                cat = R.drawable.ic_weekend_black_24dp;
                break;
            case multimedia:
                cat = R.drawable.ic_android_black_24dp;
                break;
            case clothing:
                cat = R.drawable.ic_shopping_basket_black_24dp;
                break;
            case sport:
                cat = R.drawable.ic_directions_bike_black_24dp;
                break;
            case food:
                cat = R.drawable.ic_restaurant_black_24dp;
                break;
            case game:
                cat = R.drawable.ic_videogame_asset_black_24dp;
                break;
            case tool:
                cat=R.drawable.ic_android_black_24dp;
                break;
            case hygiene:
                cat=R.drawable.ic_android_black_24dp;
                break;
            case music:
                cat = R.drawable.ic_music_note_black_24dp;
                break;
            case animal:
                cat = R.drawable.ic_directions_bike_black_24dp;
                break;
            case book:
                cat = R.drawable.ic_import_contacts_black_24dp;
                break;
            case nature:
                cat = R.drawable.ic_android_black_24dp;
                break;
            case service:
                cat = R.drawable.ic_android_black_24dp;
                break;
            case other:
                cat = R.drawable.ic_android_black_24dp;
                break;
            default:
                cat = R.drawable.ic_android_black_24dp;
                break;
        }

        return cat;
    }


}
