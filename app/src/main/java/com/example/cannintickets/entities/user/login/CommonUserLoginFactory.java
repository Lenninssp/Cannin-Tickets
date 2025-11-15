package com.example.cannintickets.entities.user.login;

public class CommonUserLoginFactory implements UserLoginFactory{
    @Override
    public UserLoginEntity create(String email, String password){
        return new CommonUserLogin(email, password);
    }
}
