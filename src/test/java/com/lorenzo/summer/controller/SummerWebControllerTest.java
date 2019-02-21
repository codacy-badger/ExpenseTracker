package com.lorenzo.summer.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class SummerWebControllerTest {

    @Test
    public void summerWebController_greeting_returnsAnGreetingMessage() {
        SummerWebController summerWebControllerTest = new SummerWebController();

        String res = summerWebControllerTest.greeting("Lorenzo");

        assertEquals("Hello, Lorenzo", res);
    }
}