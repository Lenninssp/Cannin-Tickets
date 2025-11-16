package com.example.cannintickets.models.events.modify;

public class ModifyEventRequestModel {
    private final String id;
    private final String name;
    private final String description;
    private final String eventDate;
    private final String location;
    private final Boolean isPrivate;

    public ModifyEventRequestModel(
            String id,
            String name,
            String description,
            String location,
            String eventDate,
            Boolean isPrivate
    ){
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID is required");
        }
        this.name = name;
        this.id = id;
        this.description = description;
        this.location = location;
        this.isPrivate = isPrivate;
        this.eventDate = eventDate;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public Boolean isPrivate() {
        return isPrivate;
    }

    public String getEventDate() {
        return eventDate;
    }
}
