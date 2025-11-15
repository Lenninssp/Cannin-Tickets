package com.example.cannintickets.entities.user.login;

public interface UserLoginFactory {
    UserLoginEntity create(String email, String password);
}
