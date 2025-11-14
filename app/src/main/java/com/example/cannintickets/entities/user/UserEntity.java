package com.example.cannintickets.entities.user;

public interface UserEntity {
    boolean isPasswordValid();
    boolean isUsernameValid();
    boolean isRoleValid();
    boolean isEmailValid();
    boolean isValid();

    String getUsername();
    String getPassword();
    String getEmail();
    String getRole();
}
