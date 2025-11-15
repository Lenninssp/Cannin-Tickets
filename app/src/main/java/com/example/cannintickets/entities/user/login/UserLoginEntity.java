package com.example.cannintickets.entities.user.login;

public interface UserLoginEntity {
    boolean isEmailValid();
    String[] isValid();
    String getEmail();
    String getPassword();
}
