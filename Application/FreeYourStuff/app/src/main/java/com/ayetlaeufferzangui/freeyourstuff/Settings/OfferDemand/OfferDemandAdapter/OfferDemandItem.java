package com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand.OfferDemandAdapter;


import com.ayetlaeufferzangui.freeyourstuff.Model.Item;

/**
 * Created by lothairelaeuffer on 20/01/2018.
 */

public class OfferDemandItem extends ListOfferDemand {

    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public int getType() {
        return TYPE_OFFER_DEMAND;
    }
}
