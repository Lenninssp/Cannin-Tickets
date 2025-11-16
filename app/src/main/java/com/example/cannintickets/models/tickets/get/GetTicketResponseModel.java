package com.example.cannintickets.models.tickets.get;

import com.example.cannintickets.models.events.get.GetEventResponseModel;
public class GetTicketResponseModel {
    private String id;
    private String eventId;
    private String name;
    private double price;
    private Integer capacity;
    private Integer sold;

    private String error;

    public GetTicketResponseModel(String error) {
        this.error = error;
    }

    public GetTicketResponseModel(
            String id,
            String name,
            double price,
            String eventId,
            Integer capacity,   // nullable
            Integer sold         // nullable
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.eventId = eventId;
        this.capacity = capacity;
        this.sold = sold;
    }

    public boolean hasError() {
        return error != null && !error.isEmpty();
    }

    // getters
    public String getId() { return id; }
    public String getEventId() { return eventId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public Integer getCapacity() { return capacity; }
    public Integer getSold() { return sold; }
    public String getError() { return error; }
}