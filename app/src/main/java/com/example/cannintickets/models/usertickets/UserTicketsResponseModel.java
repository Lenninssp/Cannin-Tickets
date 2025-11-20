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
    private File image;

    public UserTicketsResponseModel(String message) {
        this.message = message;
    }

    public UserTicketsResponseModel(String id, Boolean checked, String eventDate, String eventName, String location, String ticketName) {
        this.id = id;
        this.checked = checked;
        this.eventDate = eventDate;
        this.eventName = eventName;
        this.location = location;
        this.ticketName = ticketName;
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

    public File getImage() {
        return image;
    }
}
