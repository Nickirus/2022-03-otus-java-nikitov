package ru.otus;

import org.junit.jupiter.api.Test;
import ru.otus.util.TestFinder;
import ru.otus.util.TestRunner;

class BeingTestedTest {

    @Test
    void test() {
        for (Class<?> clazz : TestFinder.find("ru.otus")) {
            TestRunner.run(clazz);
        }
    }

}