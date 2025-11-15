package com.example.cannintickets.entities.event;

import java.time.LocalDateTime;

public class CommonEvent implements EventEntity{
    private static final int MAX_NAME_LENGTH = 80;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    final String name;
    final String description;
    final LocalDateTime eventDate;
    final String location;

    public CommonEvent(
            String name,
            String description,
            LocalDateTime eventDate,
            String location
    ) {
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
    }

    public boolean isEventInTheFuture() {
        if (eventDate == null) return false;
        return eventDate.isAfter(LocalDateTime.now());
    }
    public boolean isNameValid() {
        if (name == null) return false;
        String trimmed = name.trim();
        return !trimmed.isEmpty() && trimmed.length() <= MAX_NAME_LENGTH;
    }
    public boolean isDescriptionValid() {
        if (description == null) return false;
        return description.length() <= MAX_DESCRIPTION_LENGTH;
    }

    public boolean isLocationValid() {
        if (location == null) return false;
        String trimmed = location.trim();
        if (trimmed.isEmpty()) return false;
        for (char c : trimmed.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                return true;
            }
        }
        return false;
    }



}
