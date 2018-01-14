package com.ayetlaeufferzangui.freeyourstuff.List;


public class ListRecyclerView {

    private String category;
    private String title;
    private String photo;
    private String availability;
    private String distance;

    private String id_item;

    public ListRecyclerView(String category, String title, String photo, String availability, String distance, String id_item) {
        this.category = category;
        this.title = title;
        this.photo = photo;
        this.availability = availability;
        this.distance = distance;
        this.id_item = id_item;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }
}
