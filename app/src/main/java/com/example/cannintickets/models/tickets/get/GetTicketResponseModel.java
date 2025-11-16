package com.example.cannintickets.models.tickets.get;

import com.example.cannintickets.models.events.get.GetEventResponseModel;

public class GetTicketResponseModel {
    private String id;
    private String eventId;
    private String name;
    private double price;

    private String error;

    public GetTicketResponseModel(String error) { this.error = error; }

    public GetTicketResponseModel(
            String id,
            String name,
            double price,
            String eventId
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.eventId = eventId;
    }

    public boolean hasError() {
        return error != null && !error.isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getEventId() {
        return eventId;
    }
}
