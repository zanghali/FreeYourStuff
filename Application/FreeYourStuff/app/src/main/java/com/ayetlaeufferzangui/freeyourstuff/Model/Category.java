package com.ayetlaeufferzangui.freeyourstuff.Model;

import com.ayetlaeufferzangui.freeyourstuff.R;

/**
 * Created by lothairelaeuffer on 22/12/2017.
 */

public enum Category {
    furniture,
    multimedia,
    clothing,
    sport,
    food,
    game,
    tool,
    hygiene,
    music,
    animal,
    book,
    nature,
    service,
    other;

    public static int createIconUrl(Category category) {

        int cat =0;

        switch (category) {
            case furniture:
                cat = R.drawable.ic_weekend_black_24dp;
                break;
            case multimedia:
                cat = R.drawable.ic_video_library_black_24dp;
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
                cat=R.drawable.ic_build_black_24dp;
                break;
            case hygiene:
                cat=R.drawable.ic_room_service_black_24dp;
                break;
            case music:
                cat = R.drawable.ic_music_note_black_24dp;
                break;
            case animal:
                cat = R.drawable.ic_pets_black_24dp;
                break;
            case book:
                cat = R.drawable.ic_import_contacts_black_24dp;
                break;
            case nature:
                cat = R.drawable.ic_nature_black_24dp;
                break;
            case service:
                cat = R.drawable.ic_local_laundry_service_black_24dp;
                break;
            case other:
                cat = R.drawable.ic_flag_black_24dp;
                break;
            default:
                cat = R.drawable.ic_flag_black_24dp;
                break;
        }

        return cat;
    }
}
