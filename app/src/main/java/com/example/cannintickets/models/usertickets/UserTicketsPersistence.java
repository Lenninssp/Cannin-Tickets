package com.example.cannintickets.models.usertickets;

import java.util.Date;

public class UserTicketsPersistence {
    private String id;
    private Boolean checked;
    private Date eventDate;
    private String eventId;
    private String eventName;
    private String location;
    private String ticketId;
    private String ticketName;
    private String userEmail;

    public UserTicketsPersistence(){}

    public UserTicketsPersistence(String id, Boolean checked, Date eventDate, String eventId, String eventName, String location, String ticketId, String ticketName, String userEmail) {
        this.checked = checked;
        this.eventDate = eventDate;
        this.eventId = eventId;
        this.eventName = eventName;
        this.location = location;
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.userEmail = userEmail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getLocation() {
        return location;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}


