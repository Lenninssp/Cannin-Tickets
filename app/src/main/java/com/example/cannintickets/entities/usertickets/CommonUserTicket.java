package com.example.cannintickets.entities.usertickets;

import java.time.LocalDateTime;
import java.util.Date;

public class CommonUserTicket implements UserTicketEntity {

    private String id;
    private Boolean checked;
    private LocalDateTime eventDate;
    private String eventId;
    private String eventName;
    private String location;
    private String ticketId;
    private String ticketName;
    private String userEmail;

    public CommonUserTicket(Boolean checked, LocalDateTime eventDate, String eventId, String eventName, String location, String ticketId, String ticketName, String userEmail) {
        this.checked = checked;
        this.eventDate = eventDate;
        this.eventId = eventId;
        this.eventName = eventName;
        this.location = location;
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.userEmail = userEmail;
    }

    @Override
    public void updateChecked(boolean newChecked) {
        this.checked = newChecked;
    }

    public String getId() {
        return id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public LocalDateTime getEventDate() {
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
