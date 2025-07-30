package agent;

import java.util.*;

public class TrackerAdvice {
    private static final Map<String, Set<String>> methodToTestMap = new HashMap<>();

    public static void logMethodCall(String methodName, String testName) {
        methodToTestMap.computeIfAbsent(methodName, k -> new HashSet<>()).add(testName);
    }

    public static Map<String, Set<String>> getMethodToTestMap() {
        return methodToTestMap;
    }
}
