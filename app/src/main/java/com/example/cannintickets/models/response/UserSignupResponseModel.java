
package com.example.cannintickets.models.response;

public class UserSignupResponseModel {
    String username;
    String email;
    String role;

    public UserSignupResponseModel(String name, String email, String role) {
        this.email = email;
        this.username = name;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
