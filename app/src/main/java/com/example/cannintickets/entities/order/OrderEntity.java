package com.example.cannintickets.entities.order;

import java.time.LocalDateTime;

public interface OrderEntity {

    // Getter methods (no implementation, just the signature)
    LocalDateTime getCreatedAt();
    String getCustomerEmail();
    String getEventId();
    String getEventName();
    String getPaymentIntentId();
    Integer getQuantity();
    String getTicketId();
    String getTicketName();
    Double getTicketPrice();
    Double getTotal();
    LocalDateTime getUpdatedAt();

}
