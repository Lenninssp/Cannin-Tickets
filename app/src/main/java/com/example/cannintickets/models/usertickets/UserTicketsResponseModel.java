package com.example.cannintickets.models.usertickets;

import java.io.File;
import java.util.Date;

public class UserTicketsResponseModel {
    private String id;
    private Boolean checked;
    private String eventDate;
    private String eventName;
    private String location;
    private String ticketName;
    private String message;
    private String userEmail;

    public UserTicketsResponseModel(String message) {
        this.message = message;
    }

    public UserTicketsResponseModel(String id, Boolean checked, String eventDate, String eventName, String location, String ticketName, String userEmail) {
        this.id = id;
        this.checked = checked;
        this.eventDate = eventDate;
        this.eventName = eventName;
        this.location = location;
        this.ticketName = ticketName;
        this.userEmail = userEmail;
    }

    public boolean hasError() {
        return message != null && !message.isEmpty();
    }

    public String getId() {
        return id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getLocation() {
        return location;
    }

    public String getTicketName() {
        return ticketName;
    }

    public String getMessage() {
        return message;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
