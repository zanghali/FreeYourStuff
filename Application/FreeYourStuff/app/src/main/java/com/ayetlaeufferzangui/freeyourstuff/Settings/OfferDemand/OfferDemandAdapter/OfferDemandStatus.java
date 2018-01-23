package com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand.OfferDemandAdapter;

import com.ayetlaeufferzangui.freeyourstuff.Model.Status;

/**
 * Created by lothairelaeuffer on 20/01/2018.
 */

public class OfferDemandStatus extends ListOfferDemand {


    private Status status;

    // here getters and setters
    // for title and so on, built
    // using date

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    @Override
    public int getType() {
        return TYPE_STATUS;
    }

}


