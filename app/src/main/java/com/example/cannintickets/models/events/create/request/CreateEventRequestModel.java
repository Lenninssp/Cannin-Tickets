package com.example.cannintickets.models.events.create.request;

import java.io.File;
public class CreateEventRequestModel {
    private String name;
    private String eventDate;
    private double priceGA;
    private double priceVIP;
    private String location;
    private boolean isPrivate;
    private int capacityGA;
    private int capacityVIP;
    private File coverImage;

    public CreateEventRequestModel(
        String name,
        String eventDate,
        double priceGA,
        double priceVIP,
        String location,
        boolean isPrivate,
        int capacityGA,
        int capacityVIP,
        File coverImage
    ){
        this.name = name;
        this.eventDate = eventDate;
        this.priceGA = priceGA;
        this.priceVIP = priceVIP;
        this.location = location;
        this.isPrivate = isPrivate;
        this.capacityGA = capacityGA;
        this.capacityVIP = capacityVIP;
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

    public int getCapacityGA() {
        return capacityGA;
    }

    public int getCapacityVIP() {
        return capacityVIP;
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
