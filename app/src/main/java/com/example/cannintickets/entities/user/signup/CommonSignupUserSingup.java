package com.example.cannintickets.entities.user.signup;

import com.example.cannintickets.entities.user.UserRole;

public class CommonSignupUserSingup implements UserSingupEntity {
    String username;
    String email;
    String password;
    String role;

    public CommonSignupUserSingup(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean isPasswordValid(){
        // taken from: https://www.youtube.com/watch?v=nS83eojwKac
        // taken from: https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";;
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
    public String[] isValid() {

        if (!isUsernameValid()) {
            return new String[] {
                    "ERROR",
                    "Invalid username. Requirements:\n" +
                            "- 8 to 20 characters\n" +
                            "- Only letters, numbers, dots, and underscores\n" +
                            "- Cannot start or end with '.' or '_'\n" +
                            "- Cannot contain consecutive dots or underscores ('..' or '__')"
            };
        }

        if (!isEmailValid()) {
            return new String[] {
                    "ERROR",
                    "Invalid email format. Must follow: name@domain.com"
            };
        }

        if (!isPasswordValid()) {
            return new String[] {
                    "ERROR",
                    "Invalid password. Requirements:\n" +
                            "- At least 8 characters\n" +
                            "- Must contain at least one letter\n" +
                            "- Must contain at least one number"
            };
        }

        if (!isRoleValid()) {
            return new String[] {
                    "ERROR",
                    "Invalid role. Allowed roles: USER, ADMIN"
            };
        }

        return new String[] {"OK", "Valid user"};
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
