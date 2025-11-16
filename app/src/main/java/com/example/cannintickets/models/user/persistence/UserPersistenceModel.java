package com.example.cannintickets.models.user.persistence;

import java.time.LocalDateTime;

public class UserPersistenceModel {
    private String email;
    private String username;
    private String role;
    private String createdAt;
    private String updatedAt;

    public UserPersistenceModel() {
    }
    public UserPersistenceModel(
            String email,
            String username,
            String role
    ) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = LocalDateTime.now().toString();
        this.updatedAt = LocalDateTime.now().toString();

    }
    public UserPersistenceModel(
            String email,
            String username,
            String role,
            String updatedAt
    ) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = LocalDateTime.now().toString();
        this.updatedAt = updatedAt;
    }


    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
