package com.example.cannintickets.entities.user;

public class CommonUser implements  UserEntity{
    String username;
    String email;
    String password;
    String role;

    public CommonUser(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean isPasswordValid(){
        // taken from: https://www.youtube.com/watch?v=nS83eojwKac
        // taken from: https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return password.matches(regex);
    }

    @Override
    public boolean isUsernameValid(){
        String regex = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
        return username.matches(regex);
    }

    @Override
    public boolean isEmailValid(){
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    @Override
    public boolean isRoleValid(){
        return UserRole.findByName(role) != null;
    }

    @Override
    public boolean isValid() {
        return isPasswordValid() && isEmailValid() && isRoleValid() && isUsernameValid();
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
