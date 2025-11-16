package com.example.cannintickets.entities.event;

import java.time.LocalDateTime;

public interface EventEntity {
    String[] isValid();

    boolean canModify(String userId);

    void updateName(String newName);
    void updateDescription(String newDescription);
    void updateLocation(String newLocation);
    void updateDate(LocalDateTime newDate);
    void updatePrivacy(Boolean privacy);

    String getId();
    String getName();
    String getDescription();
    LocalDateTime getEventDate();
    String getLocation();
    Boolean getIsPrivate();
    String getOrganizerId();
}