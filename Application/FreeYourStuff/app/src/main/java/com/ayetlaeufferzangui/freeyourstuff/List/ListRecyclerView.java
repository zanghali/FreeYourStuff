package com.ayetlaeufferzangui.freeyourstuff.List;


public class ListRecyclerView {

    private String title;
    private String photo;
    private int nbOfInterestedPeople;
    private double distance;
    private String availability;

    private String category;
    private String idItem;

    public ListRecyclerView(String title, String photo, int nbOfInterestedPeople, double distance, String availability, String category, String idItem) {
        this.title = title;
        this.photo = photo;
        this.nbOfInterestedPeople = nbOfInterestedPeople;
        this.distance = distance;
        this.availability = availability;
        this.category = category;
        this.idItem = idItem;
    }



    public String getTitle() {
        return title;
    }

    public String getPhoto() {
        return photo;
    }

    public int getNbOfInterestedPeople() {
        return nbOfInterestedPeople;
    }

    public double getDistance() {
        return distance;
    }

    public String getAvailability() {
        return availability;
    }

    public String getCategory() {
        return category;
    }

    public String getIdItem() {
        return idItem;
    }
}
