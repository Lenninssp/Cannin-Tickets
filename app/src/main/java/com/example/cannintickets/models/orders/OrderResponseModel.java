package com.example.cannintickets.models.orders;

import java.time.LocalDateTime;

public class OrderResponseModel {
    private LocalDateTime createdAt;
    private String eventName;
    private Integer quantity;
    private String ticketName;
    private Double ticketPrice;
    private Double total;

    private String message;

    public OrderResponseModel(String message) {
        this.message = message;
    }

    public OrderResponseModel(LocalDateTime createdAt, String eventName, Integer quantity, String ticketName, Double ticketPrice, Double total) {
        this.createdAt = createdAt;
        this.eventName = eventName;
        this.quantity = quantity;
        this.ticketName = ticketName;
        this.ticketPrice = ticketPrice;
        this.total = total;
    }

    public boolean hasError() {
        return message != null && !message.isEmpty();
    }

}
