package com.example;

import com.example.coverage.MethodCallTracker;

public class MyService {
    public void addUser() {
        MethodCallTracker.log("addUser");
        // simulate logic
    }

    public void removeUser() {
        MethodCallTracker.log("removeUser");
        // simulate logic
    }
}
