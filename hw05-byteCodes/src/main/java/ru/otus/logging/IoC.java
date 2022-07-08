package ru.otus.logging;

import ru.otus.TestLogging;
import ru.otus.TestLoggingImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IoC {
    private IoC() {
        throw new UnsupportedOperationException("This is utility class");
    }

    public static TestLogging createProxy() {
        LoggingInvocationHandler<TestLogging> handler = new LoggingInvocationHandler<>(new TestLoggingImpl(), Log.class);
        return (TestLogging) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class LoggingInvocationHandler<T> implements InvocationHandler {
        private final T instance;
        private final List<Method> targetMethods;

        LoggingInvocationHandler(T instance, Class<? extends Annotation> annotationMarker) {
            this.instance = instance;
            this.targetMethods = getTargetMethods(instance, annotationMarker);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isTargetMethod(method)) {
                System.out.println(String.format("executed method: %s, param: %s",
                        method.getName(), Arrays.toString(args)));
            }
            return method.invoke(instance, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + instance +
                    '}';
        }

        private List<Method> getTargetMethods(T instance, Class<? extends Annotation> annotationMarker) {
            return Arrays.stream(instance.getClass().getMethods())
                    .filter(method -> Arrays.stream(method.getDeclaredAnnotations())
                            .anyMatch(annotation -> annotation.annotationType().equals(annotationMarker)))
                    .collect(Collectors.toList());
        }

        private boolean isTargetMethod(Method checkedMethod) {

            return targetMethods.stream()
                    .anyMatch(method -> method.getName().equals(checkedMethod.getName())
                            && Arrays.equals(method.getParameterTypes(),
                            checkedMethod.getParameterTypes()));
        }
    }
}
