package com.example.cannintickets.models.events.create.request;

import java.io.File;
public class CreateEventRequestModel {
    private String name;
    private String description;
    private String eventDate;
    private String location;
    private boolean isPrivate;
    private String coverImage;

    public CreateEventRequestModel(
            String name,
            String description,
            String eventDate,
            String location,
            boolean isPrivate,
            String coverImage
    ){
        this.name = name;
        this.eventDate = eventDate;
        this.location = location;
        this.isPrivate = isPrivate;
        this.coverImage = coverImage;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getLocation() {
        return location;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
