package com.example.cannintickets.entities.user;

public interface UserFactory {
   UserEntity create(String username, String email, String password, String role);

}
