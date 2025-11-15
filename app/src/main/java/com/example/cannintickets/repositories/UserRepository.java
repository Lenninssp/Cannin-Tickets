package com.example.cannintickets.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cannintickets.entities.user.UserEntity;
import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class UserRepository {
    final FirebaseAuth mAuth;

    public UserRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public String[] save(UserSignupRequestModel user){
        Task<AuthResult> result;
        result = mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.w("Signup", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });

        return result.isSuccessful() ? new String[]{"SUCCESS", "The signup was successful"} : new String[]{"ERROR", "The signup failed"};
    }

}
