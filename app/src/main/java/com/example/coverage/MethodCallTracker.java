package com.example.coverage;

import agent.TrackerAdvice;

public class MethodCallTracker {
    public static void log(String methodName) {
        // Provide dummy test name or real test context
        TrackerAdvice.logMethodCall(methodName, "UNKNOWN_TEST");
    }
}
