package com.example.cannintickets.entities.user;

public class CommonUserFactory implements UserFactory {
    @Override
    public UserEntity create(String username, String email, String password, String role){
        return new CommonUser(username, email, password, role);
    }
}
