package com.example.cannintickets.entities.user.signup;

public interface UserSignupFactory {
   UserSingupEntity create(String username, String email, String password, String role);

}
