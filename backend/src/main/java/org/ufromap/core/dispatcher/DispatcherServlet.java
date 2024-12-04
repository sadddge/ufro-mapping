package org.ufromap.core.dispatcher;

import lombok.extern.java.Log;
import org.ufromap.core.annotations.*;
import org.ufromap.core.exceptions.BadRequestException;
import org.ufromap.core.exceptions.ExceptionHandlers;
import org.ufromap.core.exceptions.GlobalExceptionHandler;
import org.ufromap.core.exceptions.UnauthorizedException;
import org.ufromap.core.utils.JwtUtil;
import org.ufromap.feature.auth.controllers.AuthController;
import org.ufromap.feature.buildings.controllers.EdificioController;
import org.ufromap.feature.classrooms.controllers.SalaController;
import org.ufromap.feature.courses.controllers.AsignaturaController;
import org.ufromap.feature.lectures.controllers.ClaseController;
import org.ufromap.feature.users.controllers.UsuarioController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@WebServlet(urlPatterns = "/*")
public class DispatcherServlet extends HttpServlet {
    private final Map<String, Method> routeHandlers = new HashMap<>();
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Override
    public void init() throws ServletException {
        try {
            registerRoutes(new AuthController());
            registerRoutes(new SalaController());
            registerRoutes(new AsignaturaController());
            registerRoutes(new EdificioController());
            registerRoutes(new UsuarioController());
            registerRoutes(new ClaseController());
            registerExceptionHandlers(new ExceptionHandlers());
        } catch (Exception e) {
            throw new ServletException("Failed to register routes", e);
        }
    }

    private void registerRoutes(Object controller) {
        Class<?> clazz = controller.getClass();
        String basePath = "";

        if (clazz.isAnnotationPresent(RequestMapping.class)) {
            basePath = clazz.getAnnotation(RequestMapping.class).value();
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                GetMapping mapping = method.getAnnotation(GetMapping.class);
                routeHandlers.put("GET:" + basePath + mapping.value(), method);
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                PostMapping mapping = method.getAnnotation(PostMapping.class);
                routeHandlers.put("POST:" + basePath + mapping.value(), method);
            } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                DeleteMapping mapping = method.getAnnotation(DeleteMapping.class);
                routeHandlers.put("DELETE:" + basePath + mapping.value(), method);
            } else if (method.isAnnotationPresent(PutMapping.class)) {
                PutMapping mapping = method.getAnnotation(PutMapping.class);
                routeHandlers.put("PUT:" + basePath + mapping.value(), method);
            }
        }
    }

    private void registerExceptionHandlers(Object handlerClass) {
        for (Method method : handlerClass.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(ExceptionHandler.class)) {
                ExceptionHandler annotation = method.getAnnotation(ExceptionHandler.class);
                globalExceptionHandler.registerExceptionHandler(annotation.value(), method);
            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String method = req.getMethod();
        if ("OPTIONS".equals(method)) {
            handleOptions(resp);
            return;
        }
        String path = req.getPathInfo();
        String key = method + ":" + path;

        try {
            Method handler = matchRouteWithParams(key);
            if (!isAuthorized(handler, req)) {
                throw new UnauthorizedException("Unauthorized");
            }
            Object[] args = resolveMethodArguments(handler, path, req, resp);
            handler.invoke(handler.getDeclaringClass().getConstructor().newInstance(), args);
        } catch (Exception e) {
            handleException(e, resp);
        }

    }

    private void handleException(Throwable ex, HttpServletResponse resp) {
        try {
            Method handler = globalExceptionHandler.getHandler(ex.getClass());
            if (handler != null) {
                handler.invoke(new ExceptionHandlers(), ex, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Unhandled exception: " + ex.getMessage());
            }
        } catch (Exception e) {
            try {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Error while handling exception: " + e.getMessage());
            } catch (IOException ignored) {
            }
        }
    }

    private void handleOptions(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private Object[] resolveMethodArguments(Method handler, String path, HttpServletRequest req, HttpServletResponse res) {
        Parameter[] parameters = handler.getParameters();
        Object[] args = new Object[parameters.length];

        Map<String, String> pathParams = extractPathParams(handler, path);

        for (int i = 0; i < parameters.length; i++) {
            Annotation[] annotations = parameters[i].getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof PathParam) {
                    String paramName = ((PathParam) annotation).value();
                    args[i] = pathParams.get(paramName);
                }
            }
        }

        for (int i = 0; i < parameters.length; i++) {
            if (args[i] == null) {
                Class<?> type = parameters[i].getType();
                if (type.isAssignableFrom(HttpServletRequest.class)) {
                    args[i] = req;
                } else if (type.isAssignableFrom(HttpServletResponse.class)) {
                    args[i] = res;
                }
            }
        }

        return args;
    }

    private Map<String, String> extractPathParams(Method handler, String path) {
        Map<String, String> params = new HashMap<>();
        String routePath = getRoutePath(handler);
        if (routePath == null) {
            return params;
        }

        Pattern pattern = Pattern.compile(routePath.replaceAll("\\{([^}]+)}", "(?<$1>[^/]+)"));
        Matcher matcher = pattern.matcher(path);

        if (matcher.matches()) {
            Pattern namedGroupPattern = Pattern.compile("\\{([^}]+)}");
            Matcher namedGroupMatcher = namedGroupPattern.matcher(routePath);
            while (namedGroupMatcher.find()) {
                String name = namedGroupMatcher.group(1);
                params.put(name, matcher.group(name));
            }
        }
        return params;
    }

    private Method matchRouteWithParams(String key) {
        try {
            for (String routeKey : routeHandlers.keySet()) {
                if (routeKey.startsWith(key.split(":")[0])) {
                    String routePath = routeKey.split(":")[1];
                    Pattern pattern = Pattern.compile(routePath.replaceAll("\\{([^}]+)}", "[^/]+"));
                    Matcher matcher = pattern.matcher(key.split(":")[1]);
                    if (matcher.matches()) {
                        return routeHandlers.get(routeKey);
                    }
                }
            }
        } catch (Exception e) {
            log.severe("Error matching route: " + e.getMessage());
        }
        throw new BadRequestException("Route not found");
    }

    private String getRoutePath(Method value) {
        for (Map.Entry<String, Method> entry : routeHandlers.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey().split(":")[1];
            }
        }
        throw new BadRequestException("Route not found");
    }

    private boolean isAuthorized(Method handler, HttpServletRequest req) {
        if (!handler.isAnnotationPresent(Protected.class)) {
            return true;
        }

        String token = Arrays.stream(req.getCookies())
                .filter(cookie -> "token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException("Token not found"));

        String role = JwtUtil.getUserRole(token);
        return Arrays.asList(handler.getAnnotation(Protected.class).roles()).contains(role);
    }
}
