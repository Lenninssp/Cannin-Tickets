
package com.example.cannintickets.models.user.auth.response;

public class UserResponseModel {
    String username;
    String email;
    String role;
    String error;

    public UserResponseModel(String name, String email, String role) {
        this.email = email;
        this.username = name;
        this.role = role;
        this.error = "";
    }
    public UserResponseModel(String error) {
        this.error = error;
        this.username = "";
        this.email = "";
        this.role = "";
    }

    public boolean isSuccess() {
        return error == null || error.isEmpty();
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

    public String getError() {
        return error;
    }

}
