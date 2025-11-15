package com.example.cannintickets.repositories;
import com.example.cannintickets.models.presenters.UserResponseFormatter;
import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.example.cannintickets.models.response.UserSignupResponseModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.CompletableFuture;
public class UserAuthRepository {
    final FirebaseAuth mAuth;

    public UserAuthRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public CompletableFuture<String> currentState() {
        return CompletableFuture.completedFuture("error");
    }

    // taken from: https://firebase.google.com/docs/auth/android/start#java
    // taken from: https://www.baeldung.com/java-completablefuture
    public CompletableFuture<String> save(UserSignupRequestModel user) {
        CompletableFuture<String> future = new CompletableFuture<>();

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        future.complete("Signup was successful");
                    } else {
                        Exception e = task.getException();
                        String errorMessage = (e != null) ? e.getMessage() : "Unknown Firebase error";
                        future.completeExceptionally(new Exception(errorMessage));
                    }
                });

        return future;
    }

}
