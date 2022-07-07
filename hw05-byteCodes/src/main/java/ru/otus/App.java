package ru.otus;

import ru.otus.logging.IoC;

public class App {
    public static void main(String[] args) {
        TestLogging instance = IoC.createProxy();
        instance.calculation(10);
        instance.calculation(10, 20);
    }
}
