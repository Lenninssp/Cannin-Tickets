package com.example.cannintickets.entities.event;

import com.example.cannintickets.services.EventValidator;

import java.time.LocalDateTime;

public class CommonEvent implements EventEntity {
    private String id;
    private String name;
    private String description;
    private LocalDateTime eventDate;
    private String location;
    private Boolean isPrivate;
    private String organizerId;

    public CommonEvent(
            String name,
            String description,
            LocalDateTime eventDate,
            String location,
            Boolean isPrivate,
            String organizerId
    ) {
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.isPrivate = isPrivate;
        this.organizerId = organizerId;
    }

    public CommonEvent(
            String id,
            String name,
            String description,
            LocalDateTime eventDate,
            String location,
            Boolean isPrivate,
            String organizerId
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.isPrivate = isPrivate;
        this.organizerId = organizerId;
    }

    @Override
    public String[] isValid() {
        return EventValidator.validateEvent(this);
    }

    public boolean canModify(String userId) {
        if (userId == null || organizerId == null) {
            return false;
        }
        return organizerId.equals(userId);
    }

    public void updateName(String newName) {
        String error = EventValidator.validateName(newName);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        this.name = newName.trim();
    }

    public void updateDescription(String newDescription) {
        String error = EventValidator.validateDescription(newDescription);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        this.description = newDescription;
    }

    public void updateLocation(String newLocation) {
        String error = EventValidator.validateLocation(newLocation);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        this.location = newLocation.trim();
    }

    public void updateDate(LocalDateTime newDate) {
        String error = EventValidator.validateDate(newDate);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        this.eventDate = newDate;
    }

    public void updatePrivacy(Boolean privacy) {
        if (privacy == null) {
            throw new IllegalArgumentException("Privacy cannot be null");
        }
        this.isPrivate = privacy;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getEventDate() { return eventDate; }
    public String getLocation() { return location; }
    public Boolean getIsPrivate() { return isPrivate; }
    public String getOrganizerId() { return organizerId; }
}