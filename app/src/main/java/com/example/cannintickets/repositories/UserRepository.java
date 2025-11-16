package com.example.cannintickets.repositories;

import androidx.annotation.NonNull;

import com.example.cannintickets.models.user.persistence.UserPersistenceModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class UserRepository {
    final FirebaseFirestore db;

    public UserRepository() { this.db = FirebaseFirestore.getInstance();}

    //taken from: https://firebase.google.com/docs/firestore/query-data/get-data?_gl=1*d5ol3o*_up*MQ..*_ga*ODI0OTAxNDAwLjE3NjMyMzQ5OTY.*_ga_CW55HF8NVT*czE3NjMyMzQ5OTUkbzEkZzAkdDE3NjMyMzQ5OTUkajYwJGwwJGgw
    public CompletableFuture<UserPersistenceModel> get(String email) {
        CompletableFuture<UserPersistenceModel> future = new CompletableFuture<>();
        db.collection("User")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot.isEmpty()) {
                        future.complete(null);
                        return;
                    }
                    DocumentSnapshot doc = querySnapshot.getDocuments().get(0);
                    UserPersistenceModel user = doc.toObject(UserPersistenceModel.class);
                    future.complete(user);
                })
                .addOnFailureListener(future::completeExceptionally);
        return future;
    }

    // taken from: https://firebase.google.com/docs/firestore/quickstart?_gl=1*89v0o6*_up*MQ..*_ga*ODI0OTAxNDAwLjE3NjMyMzQ5OTY.*_ga_CW55HF8NVT*czE3NjMyMzQ5OTUkbzEkZzAkdDE3NjMyMzQ5OTUkajYwJGwwJGgw
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
