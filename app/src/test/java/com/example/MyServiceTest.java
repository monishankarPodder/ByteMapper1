package com.example;

import agent.TrackerAdvice;
import org.junit.Rule;
import org.junit.Test;

public class MyServiceTest {

    @Test
    public void testAddUser() {
        TrackerAdvice.setCurrentTest("testAddUser");
        new MyService().addUser();
    }

    @Test
    public void testRemoveUser() {
        TrackerAdvice.setCurrentTest("testRemoveUser");
        new MyService().removeUser();
    }
}
