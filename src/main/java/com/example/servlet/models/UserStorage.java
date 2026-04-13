package com.example.servlet.models;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {
    private static final Map<String, User> users = new HashMap<>();

    public static void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static boolean validateUser(String username, String password) {
        User user = getUser(username);
        return user != null && user.getPassword().equals(password);
    }
}