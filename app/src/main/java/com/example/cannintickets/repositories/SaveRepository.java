package com.example.cannintickets.repositories;

import androidx.annotation.NonNull;

import com.example.cannintickets.models.orders.OrderPersistenceModel;
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

public class SaveRepository {
    final FirebaseFirestore db;

    public SaveRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<String> delete(String userId, String eventId) {
        CompletableFuture<String> future = new CompletableFuture<>();

        db.collection("Save")
                .whereEqualTo("userId", userId)
                .whereEqualTo("eventId", eventId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot.isEmpty()) {
                        future.complete("Nothing to delete");
                        return;
                    }

                    for (DocumentSnapshot doc : querySnapshot) {
                        doc.getReference().delete()
                                .addOnSuccessListener(aVoid ->
                                        future.complete("The save was successfully deleted"))
                                .addOnFailureListener(e ->
                                        future.complete("Error deleting save: " + e.getMessage()));
                    }
                })
                .addOnFailureListener(future::completeExceptionally
                );

        return future;
    }

    public CompletableFuture<String> getFromUserAndEventId(String userId, String eventId) {
        CompletableFuture<String> future = new CompletableFuture<>();

        db.collection("Save")
                .whereEqualTo("userId", userId)
                .whereEqualTo("eventId", eventId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot.isEmpty()) {
                        future.complete(null);
                    } else {
                        future.complete(querySnapshot.getDocuments().get(0).getId());
                    }
                })
                .addOnFailureListener(future::completeExceptionally
                );

        return future;
    }

    public CompletableFuture<List<String>> getFromUser(String userId) {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        db.collection("Save")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<String> looplist = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String eventId = document.getString("eventId");
                                looplist.add(eventId);
                            }
                            future.complete(looplist);
                        }
                        else {
                            future.completeExceptionally(null);
                        }
                    }
                });

        return future;
    }



    public CompletableFuture<Boolean> create(String userEmail, String eventId) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Map<String, Object> newSave = new HashMap<>();
        newSave.put("eventId", eventId);
        newSave.put("userId", userEmail);
        db.collection("Save")
                .add(newSave)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        future.complete(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        future.complete(false);
                    }
                });
        return future;
    }
}
