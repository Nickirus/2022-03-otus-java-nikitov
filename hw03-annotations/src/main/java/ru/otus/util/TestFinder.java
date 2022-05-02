package ru.otus.util;

import lombok.experimental.UtilityClass;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import ru.otus.annotation.Test;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class TestFinder {
    public Set<Class<?>> find(String packageForScan) {
        Reflections reflections = new Reflections(packageForScan, Scanners.values());
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Test.class);

        return methods.stream().map(Method::getDeclaringClass).collect(Collectors.toSet());
    }
}
