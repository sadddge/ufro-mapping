package org.ufromap.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.java.Log;
import org.ufromap.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@WebServlet(urlPatterns = "/*")
public class DispatcherServlet extends HttpServlet {
    private final Map<String, Method> routeHandlers = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try {
            registerRoutes(new AuthController());
            registerRoutes(new SalaController());
            registerRoutes(new AsignaturaController());
            registerRoutes(new EdificioController());
            registerRoutes(new UsuarioController());
            registerRoutes(new ClaseController());
        } catch (Exception e) {
            throw new ServletException("Failed to register routes", e);
        }
    }

    private void registerRoutes(Object controller) throws Exception {
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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String method = req.getMethod();
        String path = req.getPathInfo();
        String key = method + ":" + path;

        Method handler = matchRouteWithParams(key);

        if (handler != null) {
            try {
                // Obtener argumentos para el método
                Object[] args = resolveMethodArguments(handler, path, req, resp);
                handler.invoke(handler.getDeclaringClass().getConstructor().newInstance(), args);
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Error processing request: " + e.getMessage());
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Route not found");
        }
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
        System.out.println("Route path: " + routePath); // /api/asignaturas/{id}
        System.out.println("Path: " + path); // /api/asignaturas/1
        if (routePath == null) {
            return params;
        }

        Pattern pattern = Pattern.compile(routePath.replaceAll("\\{([^}]+)}", "(?<$1>[^/]+)"));
        Matcher matcher = pattern.matcher(path);

        if (matcher.matches()) {
            System.out.println("Matches");
            Pattern namedGroupPattern = Pattern.compile("\\{([^}]+)}");
            Matcher namedGroupMatcher = namedGroupPattern.matcher(routePath);
            while (namedGroupMatcher.find()) {
                String name = namedGroupMatcher.group(1);
                System.out.println("Name: " + name);
                System.out.println("Value: " + matcher.group(name));
                params.put(name, matcher.group(name));
            }
        }
        return params;
    }

    private Method matchRouteWithParams(String key) {
        try {
            for (String routeKey : routeHandlers.keySet()) {
                if (routeKey.startsWith(key.split(":")[0])) { // Match method (e.g., GET)
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
        return null;
    }

    private String getRoutePath(Method value) {
        for (Map.Entry<String, Method> entry : routeHandlers.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey().split(":")[1];
            }
        }
        return null;
    }
}
