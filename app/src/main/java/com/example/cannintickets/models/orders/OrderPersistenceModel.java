package com.example.cannintickets.models.orders;

public class OrderPersistenceModel {
    private String id;
    private String customerEmail;
    private String ticketId;
    private String ticketName;
    private Double ticketPrice;
    private Integer quantity;
    private Double amount;
    private String paymentIntentId;
    private String status;
    private String eventName;
    public OrderPersistenceModel() {}
    public OrderPersistenceModel(
            String id,
            String customerEmail,
            String ticketId,
            String ticketName,
            Double ticketPrice,
            Integer quantity,
            Double amount,
            String paymentIntentId,
            String status,
            String eventName
    ) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.ticketPrice = ticketPrice;
        this.quantity = quantity;
        this.amount = amount;
        this.paymentIntentId = paymentIntentId;
        this.status = status;
        this.eventName = eventName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
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

    public String getStatus() {
        return status;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getTicketName() {
        return ticketName;
    }
}
