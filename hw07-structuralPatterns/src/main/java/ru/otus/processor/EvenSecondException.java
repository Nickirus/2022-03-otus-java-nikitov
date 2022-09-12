package ru.otus.processor;

public class EvenSecondException extends RuntimeException {

    public EvenSecondException(int second) {
        super("even second: " + second);
    }
}
