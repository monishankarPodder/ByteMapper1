package com.example;

import agent.TrackerAdvice;
import org.junit.AfterClass;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class TestLifecycleTracker {

    @AfterClass
    public static void writeMapping() {
        try (FileWriter writer = new FileWriter("target/method_test_mapping.json")) {  // <-- changed path
            writer.write("{\n");
            boolean first = true;

            for (Map.Entry<String, Set<String>> entry : TrackerAdvice.getMethodToTestMap().entrySet()) {
                if (!first) writer.write(",\n");

                writer.write("  \"" + entry.getKey() + "\": [");
                writer.write(String.join(", ", entry.getValue().stream().map(s -> "\"" + s + "\"").toList()));
                writer.write("]");
                first = false;
            }

            writer.write("\n}\n");
            System.out.println(">>> Mapping written to target/method_test_mapping.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
