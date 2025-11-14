package com.example.cannintickets.entities.user;

public enum UserRole {
    BUYER,
    SELLER,
    ADMIN;

    // taken from: https://www.baeldung.com/java-search-enum-values
    public static UserRole findByName(String name) {
        UserRole result = null;
        for (UserRole role : values()){
            if (role.name().equalsIgnoreCase(name)){
                result = role;
                break;
            }
        }
        return result;
    }
}
