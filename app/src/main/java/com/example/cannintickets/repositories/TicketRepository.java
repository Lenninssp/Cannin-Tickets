package com.example.cannintickets.repositories;

import androidx.annotation.NonNull;

import com.example.cannintickets.models.tickets.persistence.TicketPersistenceModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TicketRepository {

    final FirebaseFirestore db;

    public TicketRepository() { db = FirebaseFirestore.getInstance();}

    public CompletableFuture<String> delete(String ticketId) {
        CompletableFuture<String> future = new CompletableFuture<>();
        db.collection("Ticket").document(ticketId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        future.complete("The ticket was successfully deleted");
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

    public CompletableFuture<TicketPersistenceModel> get(String id) {
        CompletableFuture<TicketPersistenceModel> future = new CompletableFuture<>();
        db.collection("Ticket").document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            TicketPersistenceModel ticket = document.toObject(TicketPersistenceModel.class);
                            assert ticket != null;
                            ticket.setId(document.getId());
                            future.complete(ticket);
                        }
                        else {
                            future.complete(null);
                        }
                    }
                });
        return future;
    }
    public CompletableFuture<List<TicketPersistenceModel>> getFromEvent(String eventId){
        CompletableFuture<List<TicketPersistenceModel>> future = new CompletableFuture<>();
        db.collection("Ticket")
                .whereEqualTo("eventId", eventId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<TicketPersistenceModel> loopList = new ArrayList<>();
                            for (QueryDocumentSnapshot document: task.getResult()){
                                TicketPersistenceModel ticket = document.toObject(TicketPersistenceModel.class);
                                ticket.setId(document.getId());
                                loopList.add(ticket);
                            }
                            future.complete(loopList);
                        } else {
                            future.complete(null);
                        }
                    }
                });
        return future;
    }

    public CompletableFuture<String> modify(TicketPersistenceModel ticket) {
        CompletableFuture<String> future = new CompletableFuture<>();
        Map<String, Object> newTicket = new HashMap<>();
        newTicket.put("name", ticket.getName());
        newTicket.put("eventId", ticket.getEventId());
        newTicket.put("price", ticket.getPrice());
        newTicket.put("sold", ticket.getSold());
        newTicket.put("capacity", ticket.getCapacity());
        db.collection("Ticket").document(ticket.getId()).update(newTicket)
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

    public CompletableFuture<String> create(TicketPersistenceModel ticket) {
        CompletableFuture<String> future = new CompletableFuture<>();
        Map<String, Object> newTicket = new HashMap<>();
        newTicket.put("name", ticket.getName());
        newTicket.put("eventId", ticket.getEventId());
        newTicket.put("price", ticket.getPrice());
        newTicket.put("sold", ticket.getSold());
        newTicket.put("capacity", ticket.getCapacity());

        db.collection("Ticket")
                .add(newTicket)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        future.complete("The ticket was successfully created");
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
