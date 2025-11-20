package com.example.cannintickets.entities.usertickets;

import java.time.LocalDateTime;
import java.util.Date;

public interface UserTicketFactory {
    UserTicketEntity create(
            Boolean checked,
            LocalDateTime eventDate,
            String eventId,
            String eventName,
            String location,
            String ticketId,
            String ticketName,
            String userEmail
    );
}
