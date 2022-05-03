package ru.otus;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

public class SecondBeingTested {

    @Before
    private void init() {
        System.out.print("@Before. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
        throw new NullPointerException();
    }

    @After
    private void cleanUp() {
        System.out.print("@After. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @Test
    public void test1() {
        System.out.print("@Test - " + "test1. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @Test
    public void test2() {
        System.out.print("@Test - " + "test2. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }
}
