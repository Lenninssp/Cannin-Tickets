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
    @Override
    public String[] isValid() {
        // Used ChatGPT to generate the error messages, aka to make them more verbose about what happened
        if (!isEventInTheFuture()) {
            return new String[]{
                    "ERROR",
                    "The event date must be in the future. Please choose a date and time after the current moment."
            };
        }

        if (!isNameValid()) {
            if (name == null || name.trim().isEmpty()) {
                return new String[]{
                        "ERROR",
                        "The event name cannot be empty. Please provide a title for your event."
                };
            }
            if (name.trim().length() > MAX_NAME_LENGTH) {
                return new String[]{
                        "ERROR",
                        "The event name is too long. Maximum allowed length is "
                                + MAX_NAME_LENGTH + " characters."
                };
            }
            return new String[]{"ERROR", "The event name is invalid."};
        }

        if (!isDescriptionValid()) {
            if (description == null) {
                return new String[]{
                        "ERROR",
                        "The event description cannot be null. Please write a short description of the event."
                };
            }
            if (description.length() > MAX_DESCRIPTION_LENGTH) {
                return new String[]{
                        "ERROR",
                        "The event description is too long. Maximum allowed length is "
                                + MAX_DESCRIPTION_LENGTH + " characters."
                };
            }
            return new String[]{"ERROR", "The event description is invalid."};
        }

        if (!isLocationValid()) {
            if (location == null || location.trim().isEmpty()) {
                return new String[]{
                        "ERROR",
                        "The event location cannot be empty. Please specify where the event takes place."
                };
            }
            return new String[]{
                    "ERROR",
                    "The event location must contain at least one letter or number. Avoid only using symbols."
            };
        }

        return new String[]{"SUCCESS", "The event is valid."};
    }



}
