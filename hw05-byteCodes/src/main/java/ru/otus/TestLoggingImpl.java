package ru.otus;

import ru.otus.logging.Log;

public class TestLoggingImpl implements TestLogging {
    @Log
    public void calculation(int param) {
        System.out.println("calculation from " + TestLoggingImpl.class.getSimpleName());
    }

    @Log
    public void calculation(int param, int param2) {
        System.out.println("calculation from " + TestLoggingImpl.class.getSimpleName());
    }
}