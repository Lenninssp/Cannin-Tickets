package com.example.cannintickets.models.events.persistence;

import java.io.File;
import java.time.LocalDateTime;

public class EventPersistenceModel {
    private String id;
    private String name;
    private String description;
    private String creationDate;
    private String eventDate;
    private String location;
    private Boolean isPrivate;
    private String organizerImageUrl;
    private String organizerId;

    public EventPersistenceModel() {}
    public EventPersistenceModel(
            String name,
            String description,
            String eventDateISO,
            String location,
            Boolean isPrivate,
            String organizerImageUrl,
            String organizerId
    ) {
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now().toString();
        this.eventDate = eventDateISO;
        this.location = location;
        this.isPrivate = isPrivate;
        this.organizerImageUrl
                = organizerImageUrl;
        this.organizerId = organizerId;
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

    public String getOrganizerImageUrl() {
        return organizerImageUrl;
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

}