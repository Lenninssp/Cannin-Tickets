package com.example.cannintickets.models.user.auth;

public class UserLoginRequestModel {
    String email;
    String password;

    public UserLoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
