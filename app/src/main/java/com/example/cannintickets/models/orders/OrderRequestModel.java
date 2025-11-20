package com.example.cannintickets.models.orders;

public class OrderRequestModel {
    private String eventId;
    private String paymentIntentId;
    private Integer quantity;
    private String ticketId;


    public OrderRequestModel(String eventId, String paymentIntentId, Integer quantity, String ticketId) {
        this.eventId = eventId;
        this.paymentIntentId = paymentIntentId;
        this.quantity = quantity;
        this.ticketId = ticketId;
    }


    public String getEventId() {
        return eventId;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getTicketId() {
        return ticketId;
    }
}


