
package com.example.cannintickets.models.response;

public class UserSignupResponseModel {
    String username;
    String email;
    String role;
    String error;
    boolean failed;

    public UserSignupResponseModel(String name, String email, String role) {
        this.email = email;
        this.username = name;
        this.role = role;
        this.error = "";
        this.failed = false;
    }
    public UserSignupResponseModel(String error) {
        this.error = error;
        this.username = "";
        this.email = "";
        this.role = "";
        this.failed = true;
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

    public boolean getFailed() {
        return failed;
    }
}
