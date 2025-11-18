package com.example.cannintickets.repositories;
import com.example.cannintickets.models.user.auth.UserLoginRequestModel;
import com.example.cannintickets.models.user.auth.UserSignupRequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.CompletableFuture;
public class UserAuthRepository {
    final FirebaseAuth mAuth;

    public UserAuthRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser currentUser() {
        return mAuth.getCurrentUser();
    }

    public void logout() {
        mAuth.signOut();
    }

    public CompletableFuture<String> login(UserLoginRequestModel user) {
        CompletableFuture<String> future = new CompletableFuture<>();
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        future.complete("Log in was successfull");
                    }
                    else {
                        Exception e = task.getException();
                        String errorMessage = (e != null) ? e.getMessage() : "Authentication failed";
                        future.completeExceptionally(new Exception(errorMessage));                    }
                });
        return future;
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
