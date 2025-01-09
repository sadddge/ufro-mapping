package org.ufromap.core.exceptions;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {
    private final Map<Class<? extends Throwable>, Method> exceptionHandlers = new HashMap<>();

    public void registerExceptionHandler(Class<? extends Throwable> exceptionClass, Method handler) {
        exceptionHandlers.put(exceptionClass, handler);
    }
    public Method getHandler(Class<? extends Throwable> exceptionClass) {
        return exceptionHandlers.get(exceptionClass);
    }
}
