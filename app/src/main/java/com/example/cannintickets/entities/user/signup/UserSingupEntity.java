package com.example.cannintickets.entities.user.signup;

public interface UserSingupEntity {
    boolean isPasswordValid();
    boolean isUsernameValid();
    boolean isRoleValid();
    boolean isEmailValid();
    boolean canCreateEvents();
    String[] isValid();


}
