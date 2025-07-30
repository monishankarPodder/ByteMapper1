package agent;

import java.util.*;

public class TrackerAdvice {
    private static final ThreadLocal<String> currentTest = new ThreadLocal<>();
    private static final Map<String, Set<String>> methodToTestMap = new HashMap<>();

    public static void setCurrentTest(String testName) {
        currentTest.set(testName);
        System.out.println(">>> Current test set to: " + testName);
    }

    public static void logMethod(String methodName) {
        String testName = currentTest.get();
        if (testName != null) {
            methodToTestMap.computeIfAbsent(methodName, k -> new HashSet<>()).add(testName);
            System.out.println(">>> Logged method: " + methodName + " for test: " + testName);
        } else {
            System.out.println(">>> Skipping logging method: " + methodName + " (no test context)");
        }
    }

    public static Map<String, Set<String>> getMethodToTestMap() {
        System.out.println(">>> Mapping size: " + methodToTestMap.size());
        return methodToTestMap;
    }
}
