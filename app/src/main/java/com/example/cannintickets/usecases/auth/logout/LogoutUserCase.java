package com.example.cannintickets.usecases.auth.logout;

import com.example.cannintickets.repositories.UserAuthRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.CompletableFuture;

public class LogoutUserCase {
    final UserAuthRepository repo;

    public LogoutUserCase() {
        this.repo = new UserAuthRepository();
    }

    public String[] execute(){
        repo.logout();
        FirebaseUser user = repo.currentUser();
        if (user == null) {
            return new String[]{"SUCCESS", "the user was successfully logged out"};
        }else {
            return new String[]{"SUCCESS", "there was an error loging out the user"};

        }
    }
}
