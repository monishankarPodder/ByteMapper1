package agent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TrackerAdvice {
    private static final ThreadLocal<String> currentTest = new ThreadLocal<>();
    private static final Map<String, Set<String>> methodToTestMap = new ConcurrentHashMap<>();

    public static void setCurrentTest(String testName) {
        currentTest.set(testName);
    }

    public static void logMethod(String methodName) {
        String test = currentTest.get();
        if (test != null) {
            methodToTestMap.computeIfAbsent(methodName, k -> new HashSet<>()).add(test);
        }
    }

    public static Map<String, Set<String>> getMethodToTestMap() {
        return methodToTestMap;
    }
}
