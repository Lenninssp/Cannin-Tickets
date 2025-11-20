package com.example.cannintickets.models.orders;

import java.time.LocalDateTime;

public class OrderPersistenceModel {
    private String id;
    private String customerEmail;
    private String ticketId;
    private String ticketName;
    private Double ticketPrice;
    private Integer quantity;
    private Double total;
    private String paymentIntentId;
    private String eventName;
    private String eventId;
    private String createdAt;
    private String updatedAt;
    public OrderPersistenceModel() {}
    public OrderPersistenceModel(
            String customerEmail,
            String ticketId,
            String ticketName,
            Double ticketPrice,
            Integer quantity,
            Double total,
            String paymentIntentId,
            String eventName,
            String eventId,
            String createdAt,
            String updatedAt
    ) {
        this.customerEmail = customerEmail;
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.ticketPrice = ticketPrice;
        this.quantity = quantity;
        this.total = total;
        this.paymentIntentId = paymentIntentId;
        this.eventName = eventName;
        this.eventId = eventId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Double getTotal() {
        return total;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getEventName() {
        return eventName;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public String getEventId() {
        return eventId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
