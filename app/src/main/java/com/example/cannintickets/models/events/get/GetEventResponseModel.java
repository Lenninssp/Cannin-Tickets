package com.example.cannintickets.models.events.get;

import java.io.File;

public class GetEventResponseModel {
    private String id;
    private String name;
    private String description;
    private String eventDate;
    private String location;
    private File coverImage;
    private String organizerId;

    private String error;


    public GetEventResponseModel(String error) {
        this.error = error;
    }

    public GetEventResponseModel(
            String id,
            String name,
            String description,
            String eventDate,
            String location,
            File coverImage,
            String organizerId
    ){
        this.id = id;
        this.name = name;
        this.eventDate = eventDate;
        this.location = location;
        this.coverImage = coverImage;
        this.description = description;
        this.organizerId = organizerId;
    }

    public boolean hasError() {
        return error != null && !error.isEmpty();
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

    public File getCoverImage() {
        return coverImage;
    }

    public String getError() {
        return error;
    }

    public String getId() {
        return id;
    }

    public String getOrganizerId() {
        return organizerId;
    }
}
