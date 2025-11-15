package com.example.cannintickets.entities.user.login;

public class CommonUserLogin implements  UserLoginEntity{
    private String email;
    private String password;

    public CommonUserLogin(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean isEmailValid() {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    @Override
    public String[] isValid() {
        if (!isEmailValid()) {
            return new String[]{"ERROR", "The format of the email is not correct"};
        }
        return new String[]{"SUCCESS", "The user information is correct"};
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
