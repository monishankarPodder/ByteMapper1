package com.example.coverage;

import agent.TrackerAdvice;

public class MethodCallTracker {
    public static void log(String methodName) {
        TrackerAdvice.logMethod(methodName);
    }
}
