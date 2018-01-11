package com.ayetlaeufferzangui.freeyourstuff.List;


public class ListRecyclerView {

    private String title;
    private String photo;
    private int nbOfInterestedPeople;
    private double distance;
    private String availability;

    private String category;
    private String id_item;

    public ListRecyclerView(String title, String photo, int nbOfInterestedPeople, double distance, String availability, String category, String id_item) {
        this.title = title;
        this.photo = photo;
        this.nbOfInterestedPeople = nbOfInterestedPeople;
        this.distance = distance;
        this.availability = availability;
        this.category = category;
        this.id_item = id_item;
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

    public String getId_item() {
        return id_item;
    }

    @Override
    public String toString() {
        return "ListRecyclerView{" +
                "title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                ", nbOfInterestedPeople=" + nbOfInterestedPeople +
                ", distance=" + distance +
                ", availability='" + availability + '\'' +
                ", category='" + category + '\'' +
                ", idItem='" + id_item + '\'' +
                '}';
    }
}
