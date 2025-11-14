package com.example.cannintickets.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthService {
    private FirebaseAuth mAuth;

    public AuthService() {
        mAuth = FirebaseAuth.getInstance();

    }

    public String[] isUserSignedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            return new String[]{"ERROR", "The user is not authorized"};
        }

        return new String[]{"TRUE", "all good"};
    }
}
