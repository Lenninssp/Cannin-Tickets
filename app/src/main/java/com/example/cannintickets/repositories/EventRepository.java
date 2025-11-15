package com.example.cannintickets.repositories;

import androidx.annotation.NonNull;

import com.example.cannintickets.models.events.create.persistence.EventPersistenceModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class EventRepository {

    final FirebaseFirestore db;

    public EventRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<String> create(EventPersistenceModel event) {
        CompletableFuture<String> future = new CompletableFuture<>();
        //taken from: https://firebase.google.com/docs/firestore/quickstart#java_1
        Map<String, Object> newEvent = new HashMap<>();
        newEvent.put("name", event.getName());
        newEvent.put("description", event.getDescription());
        newEvent.put("creationDate", event.getCreationDate());
        newEvent.put("eventDate", event.getEventDate());
        newEvent.put("isPrivate", event.isPrivate());
        newEvent.put("location", event.getLocation());
        newEvent.put("organizerId", event.getOrganizerId());
        newEvent.put("organizerImageUrl", event.getCoverImage());

        db.collection("Event")
                .add(newEvent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        future.complete("The event was successfully created");

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
