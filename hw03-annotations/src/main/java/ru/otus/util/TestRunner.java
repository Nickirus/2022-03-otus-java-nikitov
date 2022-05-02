package ru.otus.util;

import lombok.experimental.UtilityClass;
import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;
import ru.otus.exception.AfterException;
import ru.otus.exception.BeforeException;
import ru.otus.exception.TestRunException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TestRunner {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\033[0;32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public <T> void run(Class<T> clazz) {
        int countAll = 0;
        int countPassed = 0;
        int countFailed = 0;
        T o = ReflectionHelper.instantiate(clazz);
        Method[] methods = o.getClass().getDeclaredMethods();
        List<Method> methodsWithBefore = new ArrayList<>();
        List<Method> methodsWithAfter = new ArrayList<>();
        List<Method> methodsWithTest = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                methodsWithBefore.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                methodsWithAfter.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                methodsWithTest.add(method);
                countAll++;
            }
        }
        try {
            for (Method testMethod : methodsWithTest) {
                T o2 = ReflectionHelper.instantiate(clazz);
                runBefore(methodsWithBefore, o2);

                try {
                    runTest(testMethod, o2);
                    countPassed++;
                } catch (TestRunException e) {
                    countFailed++;
                }

                runAfter(methodsWithAfter, o2);
            }
        } catch (BeforeException|AfterException ignored) {
            //ignored to allow multiple test classes to run
        }
        writeStatistic(countPassed, countFailed, countAll);
    }

    private static void writeStatistic(int countPassed, int countFailed, int countAll) {
        String color;
        if (countPassed == countAll) {
            color = ANSI_GREEN;
        } else {
            color = ANSI_RED;
        }
        System.out.println(color
                + String.format("---\n" + color + "Test statistic: passed - %s; failed - %s; of all - %s\n",
                countPassed, countFailed, countAll)
                + ANSI_RESET);
    }

    private static <T> void runBefore(List<Method> methodsWithBefore, T o2) {
        for (Method beforeMethod : methodsWithBefore) {
            try {
                beforeMethod.setAccessible(true);
                ReflectionHelper.callMethod(o2, beforeMethod.getName());

            } catch (Exception e) {
                System.out.println(ANSI_RED
                        + "@Before method '" + beforeMethod.getName() + "' failed"
                        + ANSI_RESET);
                System.out.println(ANSI_RED
                        + String.format("An exception - %s was thrown during before method with message: %s",
                        e.getClass(), e.getMessage())
                        + ANSI_RESET);
                throw new BeforeException();
            }
        }
    }

    private static <T> void runAfter(List<Method> methodsWithAfter, T o2) {
        for (Method afterMethod : methodsWithAfter) {
            try {
            afterMethod.setAccessible(true);
            ReflectionHelper.callMethod(o2, afterMethod.getName());
            } catch (Exception e) {
                System.out.println(ANSI_RED
                        + "@After method '" + afterMethod.getName() + "' failed"
                        + ANSI_RESET);
                System.out.println(ANSI_RED
                        + String.format("An exception - %s was thrown during after method with message: %s",
                        e.getClass(), e.getMessage())
                        + ANSI_RESET);
                throw new AfterException();
            }
        }
    }

    private static <T> void runTest(Method testMethod, T o2) {
        try {
            testMethod.setAccessible(true);
            ReflectionHelper.callMethod(o2, testMethod.getName());
            System.out.println(ANSI_GREEN + testMethod.getName() + " passed" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(ANSI_RED + testMethod.getName() + " failed" + ANSI_RESET);
            System.out.println(ANSI_RED
                    + String.format("An exception - %s was thrown during test execution with message: %s",
                    e.getClass(), e.getMessage())
                    + ANSI_RESET);
            throw new TestRunException();
        }
    }
}
