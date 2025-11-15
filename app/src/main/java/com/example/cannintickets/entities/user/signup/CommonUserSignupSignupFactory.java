package com.example.cannintickets.entities.user.signup;

public class CommonUserSignupSignupFactory implements UserSignupFactory {
    @Override
    public UserSingupEntity create(String username, String email, String password, String role){
        return new CommonSignupUserSingup(username, email, password, role);
    }
}
