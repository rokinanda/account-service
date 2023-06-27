package com.jap.microservice.accountservice;

import org.junit.jupiter.api.Test;

public class ThreadTest {
    @Test
    void mainThread() {
        var name = Thread.currentThread().getName();
        for (int i = 0; i < 1000; i++) {

        }
        System.out.println(name);
    }

    @Test
    void mainThread2() {
        var name = Thread.currentThread().getName();
        System.out.println(name);
    }
}
