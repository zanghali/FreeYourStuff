package com.ayetlaeufferzangui.freeyourstuff.List;


public class ListRecyclerView {

    private String title;
    private String photo;
    private int nbOfInterestedPeople;
    private double distance;
    private String availability;

    private String category;

    public ListRecyclerView(String title, String photo, int nbOfInterestedPeople, double distance, String availability, String category) {
        this.title = title;
        this.photo = photo;
        this.nbOfInterestedPeople = nbOfInterestedPeople;
        this.distance = distance;
        this.availability = availability;
        this.category = category;
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
}
