package com.example.cannintickets.entities.user.signup;

public interface UserSingupEntity {
    boolean isPasswordValid();
    boolean isUsernameValid();
    boolean isRoleValid();
    boolean isEmailValid();
    String[] isValid();

    String getUsername();
    String getPassword();
    String getEmail();
    String getRole();
}
