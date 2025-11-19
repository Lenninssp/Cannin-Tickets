package com.example.cannintickets.repositories;

import androidx.annotation.NonNull;

import com.example.cannintickets.models.tickets.persistence.TicketPersistenceModel;
import com.example.cannintickets.models.usertickets.UserTicketsPersistence;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class UserTicketRepository {
    final FirebaseFirestore db;

    public UserTicketRepository() { db = FirebaseFirestore.getInstance(); }

    public CompletableFuture<List<UserTicketsPersistence>> getFromEvent(String userEmail) {
        CompletableFuture<List<UserTicketsPersistence>> future = new CompletableFuture<>();
        db.collection("UserTicket")
                .whereEqualTo("eventId", userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<UserTicketsPersistence> looplist = new ArrayList<>();
                            for (QueryDocumentSnapshot document: task.getResult()){
                                UserTicketsPersistence ticket = document.toObject(UserTicketsPersistence.class);
                                ticket.setId(document.getId());
                                looplist.add(ticket);
                            }
                            future.complete(looplist);
                        }
                        else {
                            future.complete(null);
                        }
                    }
                });

        return future;
    }

    public CompletableFuture<List<UserTicketsPersistence>> getFromBuyer(String userEmail) {
        CompletableFuture<List<UserTicketsPersistence>> future = new CompletableFuture<>();
        db.collection("UserTicket")
                .whereEqualTo("userEmail", userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<UserTicketsPersistence> looplist = new ArrayList<>();
                            for (QueryDocumentSnapshot document: task.getResult()){
                                UserTicketsPersistence ticket = document.toObject(UserTicketsPersistence.class);
                                ticket.setId(document.getId());
                                looplist.add(ticket);
                            }
                            future.complete(looplist);
                        }
                        else {
                            future.complete(null);
                        }
                    }
                });

        return future;
    }

    public CompletableFuture<String> modify(UserTicketsPersistence ticket) {
        CompletableFuture<String> future = new CompletableFuture<>();
        Map<String, Object> newTicket = new HashMap<>();
        newTicket.put("checked", ticket.getChecked());
        newTicket.put("eventDate", ticket.getEventDate());
        newTicket.put("eventId", ticket.getEventId());
        newTicket.put("eventName", ticket.getEventName());
        newTicket.put("location", ticket.getLocation());
        newTicket.put("ticketId", ticket.getTicketId());
        newTicket.put("ticketName", ticket.getTicketName());
        newTicket.put("userEmail", ticket.getUserEmail());

        db.collection("UserTicket")
                .document(ticket.getId())
                .update(newTicket)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        future.complete("The ticket was successfully modified");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        future.complete(e.toString());
                    }
                });
        return future;
    }

    public CompletableFuture<String> create(UserTicketsPersistence ticket) {
        CompletableFuture<String> future = new CompletableFuture<>();
        Map<String, Object> newTicket = new HashMap<>();
        newTicket.put("checked", ticket.getChecked());
        newTicket.put("eventDate", ticket.getEventDate());
        newTicket.put("eventId", ticket.getEventId());
        newTicket.put("eventName", ticket.getEventName());
        newTicket.put("location", ticket.getLocation());
        newTicket.put("ticketId", ticket.getTicketId());
        newTicket.put("ticketName", ticket.getTicketName());
        newTicket.put("userEmail", ticket.getUserEmail());

        db.collection("UserTicket")
                .add(newTicket)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        future.complete("The user ticket was successfully created");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        future.complete(e.toString());
                    }
                });
        return future;
    }
}
