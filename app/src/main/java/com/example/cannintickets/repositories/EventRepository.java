package com.example.cannintickets.repositories;

import androidx.annotation.NonNull;

import com.example.cannintickets.models.events.persistence.EventPersistenceModel;
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

public class EventRepository {

    final FirebaseFirestore db;

    public EventRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<String> delete(String eventId) {
        CompletableFuture<String> future = new CompletableFuture<>();
        db.collection("Event").document(eventId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        future.complete("The data was successfully deleted");
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
    public CompletableFuture<String> modify(EventPersistenceModel eventPersistenceModel){
        CompletableFuture<String> future = new CompletableFuture<>();
        Map<String, Object> newEvent = new HashMap<>();
        newEvent.put("name", eventPersistenceModel.getName());
        newEvent.put("description", eventPersistenceModel.getDescription());
        newEvent.put("creationDate", eventPersistenceModel.getCreationDate());
        newEvent.put("eventDate", eventPersistenceModel.getEventDate());
        newEvent.put("isPrivate", eventPersistenceModel.getIsPrivate());
        newEvent.put("location", eventPersistenceModel.getLocation());
        newEvent.put("organizerId", eventPersistenceModel.getOrganizerId());
        newEvent.put("organizerImageUrl", eventPersistenceModel.getCoverImage());
        db.collection("Event").document(eventPersistenceModel.getId()).update(newEvent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        future.complete("The event was successfully modified");
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

    public CompletableFuture<EventPersistenceModel> get(String id) {
        CompletableFuture<EventPersistenceModel> future = new CompletableFuture<>();
        db.collection("Event").document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            EventPersistenceModel event = document.toObject(EventPersistenceModel.class);
                            assert event != null;
                            event.setId(document.getId());
                            future.complete(event);
                        } else {
                            future.complete(null);
                        }
                    }
                });
        return future;
    }

    public CompletableFuture<List<EventPersistenceModel>> getFromEmail(String email) {
        CompletableFuture<List<EventPersistenceModel>> future = new CompletableFuture<>();
        db.collection("Event")
                .whereEqualTo("organizerId", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<EventPersistenceModel> loopList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EventPersistenceModel event = document.toObject(EventPersistenceModel.class);
                                event.setId(document.getId());
                                loopList.add(event);
                            }
                            future.complete(loopList);
                        } else {
                            future.complete(null);
                        }
                    }
                });
        return future;
    }


    public CompletableFuture<List<EventPersistenceModel>> getPublic() {
        CompletableFuture<List<EventPersistenceModel>> future = new CompletableFuture<>();
        db.collection("Event")
                .whereEqualTo("isPrivate", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<EventPersistenceModel> loopList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EventPersistenceModel event = document.toObject(EventPersistenceModel.class);
                                event.setId(document.getId());
                                loopList.add(event);
                            }
                            future.complete(loopList);
                        } else {
                            future.complete(null);
                        }
                    }
                });
        return future;
    }

    public CompletableFuture<String> create(EventPersistenceModel event) {
        CompletableFuture<String> future = new CompletableFuture<>();
        //taken from: https://firebase.google.com/docs/firestore/quickstart#java_1
        Map<String, Object> newEvent = new HashMap<>();
        newEvent.put("name", event.getName());
        newEvent.put("description", event.getDescription());
        newEvent.put("creationDate", event.getCreationDate());
        newEvent.put("eventDate", event.getEventDate());
        newEvent.put("isPrivate", event.getIsPrivate());
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
