package com.example;

import agent.TrackerAdvice;
import org.junit.AfterClass;

import java.io.FileWriter;
import java.io.IOException;

public class TestLifecycleTracker {

    @AfterClass
    public static void writeMapping() {
        try (FileWriter writer = new FileWriter("app/target/method_test_mapping.json")) {
            writer.write("{\n");
            boolean first = true;
            for (var entry : TrackerAdvice.getMethodToTestMap().entrySet()) {
                if (!first) writer.write(",\n");
                writer.write("  \"" + entry.getKey() + "\": [");
                writer.write(String.join(", ", entry.getValue().stream().map(s -> "\"" + s + "\"").toList()));
                writer.write("]");
                first = false;
            }
            writer.write("\n}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
