package com.example.cannintickets.models.events.persistence;

import java.io.File;
import java.time.LocalDateTime;

public class EventPersistenceModel {
    private String id;
    private String name;
    private String description;
    private String creationDate; // ISO 8601 string
    private String eventDate;    // ISO 8601 string
    private String location;
    private Boolean isPrivate;
    private String coverImage;
    private String organizerId;

    private String organizerImageUrl;


    public EventPersistenceModel() {}
    public EventPersistenceModel(
            String name,
            String description,
            String eventDateISO,
            String location,
            Boolean isPrivate,
            String coverImage,
            String organizerId,
            String id
    ) {
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now().toString();
        this.eventDate = eventDateISO;
        this.location = location;
        this.isPrivate = isPrivate;
        this.coverImage = coverImage;
        this.organizerId = organizerId;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getLocation() {
        return location;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public String getOrganizerId() {
        return organizerId;
    }
    public String getId()  {
        return  id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getOrganizerImageUrl() { return organizerImageUrl; }

}