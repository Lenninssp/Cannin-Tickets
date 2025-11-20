package com.example.cannintickets.entities.order;

import com.example.cannintickets.entities.ticket.TicketName;

import java.time.LocalDateTime;

public class CommonOrder implements OrderEntity {
    private LocalDateTime createdAt;
    private String customerEmail;
    private String eventId;
    private String eventName;
    private String paymentIntentId;
    private Integer quantity;
    private String ticketId;
    private String ticketName;
    private Double ticketPrice;
    private Double total;
    private LocalDateTime updatedAt;

    public CommonOrder(LocalDateTime createdAt,
                       LocalDateTime updatedAt,
                       Double total,
                       String customerEmail,
                       String eventId,
                       String eventName,
                       String paymentIntentId,
                       Integer quantity,
                       String ticketId,
                       String ticketName,
                       Double ticketPrice
                       ) {
        this.createdAt = createdAt;
        this.customerEmail = customerEmail;
        this.eventId = eventId;
        this.eventName = eventName;
        this.paymentIntentId = paymentIntentId;
        this.quantity = quantity;
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.ticketPrice = ticketPrice;
        this.total = total;
        this.updatedAt = updatedAt;
    }


    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
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

    public String getTicketName() {
        return ticketName;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public Double getTotal() {
        return total;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
