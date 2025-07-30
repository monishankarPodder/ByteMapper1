package agent;

import java.util.*;

public class TrackerAdvice {
    private static final Map<String, Set<String>> methodToTestMap = new HashMap<>();
    private static final ThreadLocal<String> currentTestName = new ThreadLocal<>();

    public static void setCurrentTest(String testName) {
        currentTestName.set(testName);
    }

    public static void logMethod(String methodName) {
        String testName = currentTestName.get();
        if (testName == null) {
            testName = "UNKNOWN_TEST";
        }
        methodToTestMap.computeIfAbsent(methodName, k -> new HashSet<>()).add(testName);
    }

    public static Map<String, Set<String>> getMethodToTestMap() {
        return methodToTestMap;
    }
}
