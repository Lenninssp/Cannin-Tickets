package com.example.cannintickets.models.user.auth.request;

public class UserSignupRequestModel {
    String username;
    String email;
    String password;
    String role;

    public UserSignupRequestModel(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
