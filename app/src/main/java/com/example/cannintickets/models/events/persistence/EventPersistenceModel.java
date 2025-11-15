package com.example.cannintickets.models.events.persistence;

import java.io.File;
import java.time.LocalDateTime;

public class EventPersistenceModel {

    private final String name;
    private final String description;
    private final String creationDate; // ISO 8601 string
    private final String eventDate;    // ISO 8601 string
    private final String location;
    private final boolean isPrivate;
    private final String coverImage;
    private final String organizerId;

    public EventPersistenceModel(
            String name,
            String description,
            String eventDateISO,
            String location,
            boolean isPrivate,
            String coverImage,
            String organizerId
    ) {
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now().toString();
        this.eventDate = eventDateISO;
        this.location = location;
        this.isPrivate = isPrivate;
        this.coverImage = coverImage;
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public String getOrganizerId() {
        return organizerId;
    }
}