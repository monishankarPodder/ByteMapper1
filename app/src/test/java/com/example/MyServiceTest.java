package com.example;

import agent.TrackerAdvice;
import org.junit.Test;

public class MyServiceTest {

    @Test
    public void testAddUser() {
        TrackerAdvice.setCurrentTest("testAddUser");
        new MyService().addUser(); // This method should trigger MethodCallTracker.log(...)
    }

    @Test
    public void testRemoveUser() {
        TrackerAdvice.setCurrentTest("testRemoveUser");
        new MyService().removeUser();
    }
}
