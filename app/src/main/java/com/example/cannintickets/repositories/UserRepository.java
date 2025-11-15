package com.example.cannintickets.repositories;

import androidx.annotation.NonNull;

import com.example.cannintickets.models.user.auth.request.UserSignupRequestModel;
import com.example.cannintickets.models.user.persistence.UserPersistenceModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class UserRepository {
    final FirebaseFirestore db;

    public UserRepository() { this.db = FirebaseFirestore.getInstance();}

    public CompletableFuture<String> create(UserPersistenceModel user) {
        CompletableFuture<String> future = new CompletableFuture<>();

        Map<String, Object> newUser = new HashMap<>();
        newUser.put("username", user.getUsername());
        newUser.put("email", user.getEmail());
        newUser.put("role", user.getRole());
        newUser.put("createdAt", user.getCreatedAt());
        newUser.put("updatedAt", user.getUpdatedAt());

        db.collection("User")
                .add(newUser)
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
