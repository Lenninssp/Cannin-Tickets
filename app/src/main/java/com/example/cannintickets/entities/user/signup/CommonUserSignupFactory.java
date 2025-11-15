package com.example.cannintickets.entities.user.signup;

public class CommonUserSignupFactory implements UserSignupFactory {
    @Override
    public UserSingupEntity create(String username, String email, String password, String role){
        return new CommonUserSingup(username, email, password, role);
    }
}
