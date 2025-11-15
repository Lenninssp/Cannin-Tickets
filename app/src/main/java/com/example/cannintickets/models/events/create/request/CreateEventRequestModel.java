package com.example.cannintickets.models.events.create.request;

import java.io.File;
public class CreateEventRequestModel {
    private String name;
    private String eventDate;
    private double priceGA;
    private double priceVIP;
    private String location;
    private boolean isPrivate;
    private File coverImage;

    public CreateEventRequestModel(
        String name,
        String eventDate,
        double priceGA,
        double priceVIP,
        String location,
        boolean isPrivate,
        File coverImage
    ){
        this.name = name;
        this.eventDate = eventDate;
        this.priceGA = priceGA;
        this.priceVIP = priceVIP;
        this.location = location;
        this.isPrivate = isPrivate;
        this.coverImage = coverImage;
    }

    public String getName() {
        return name;
    }

    public String getEventDate() {
        return eventDate;
    }

    public double getPriceGA() {
        return priceGA;
    }

    public String getLocation() {
        return location;
    }

    public double getPriceVIP() {
        return priceVIP;
    }

    public File getCoverImage() {
        return coverImage;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
