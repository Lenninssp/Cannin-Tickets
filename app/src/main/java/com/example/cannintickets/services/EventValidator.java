package com.example.cannintickets.services;
import com.example.cannintickets.entities.event.EventEntity;

import java.time.LocalDateTime;

public class EventValidator {
    private static final int MAX_NAME_LENGTH = 80;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    public static String[] validateEvent(EventEntity event) {
        if (!isEventInTheFuture(event.getEventDate())) {
            return new String[]{
                    "ERROR",
                    "The event date must be in the future. Please choose a date and time after the current moment."
            };
        }

        String nameError = validateName(event.getName());
        if (nameError != null) {
            return new String[]{"ERROR", nameError};
        }

        String descriptionError = validateDescription(event.getDescription());
        if (descriptionError != null) {
            return new String[]{"ERROR", descriptionError};
        }

        String locationError = validateLocation(event.getLocation());
        if (locationError != null) {
            return new String[]{"ERROR", locationError};
        }

        return new String[]{"SUCCESS", "The event is valid."};
    }

    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "The event name cannot be empty. Please provide a title for your event.";
        }
        if (name.trim().length() > MAX_NAME_LENGTH) {
            return "The event name is too long. Maximum allowed length is " + MAX_NAME_LENGTH + " characters.";
        }
        return null;
    }

    public static String validateDescription(String description) {
        if (description == null) {
            return "The event description cannot be null. Please write a short description of the event.";
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            return "The event description is too long. Maximum allowed length is " + MAX_DESCRIPTION_LENGTH + " characters.";
        }
        return null;
    }

    public static String validateLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return "The event location cannot be empty. Please specify where the event takes place.";
        }

        String trimmed = location.trim();
        boolean hasAlphanumeric = false;
        for (char c : trimmed.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                hasAlphanumeric = true;
                break;
            }
        }

        if (!hasAlphanumeric) {
            return "The event location must contain at least one letter or number. Avoid only using symbols.";
        }

        return null;
    }

    public static String validateDate(LocalDateTime date) {
        if (date == null || !date.isAfter(LocalDateTime.now())) {
            return "Date must be in the future";
        }
        return null;
    }

    private static boolean isEventInTheFuture(LocalDateTime eventDate) {
        if (eventDate == null) return false;
        return eventDate.isAfter(LocalDateTime.now());
    }
}