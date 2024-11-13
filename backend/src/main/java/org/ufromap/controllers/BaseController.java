package org.ufromap.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.services.IService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseController<T> extends HttpServlet {
    protected final IService<T> service;
    protected Gson gson = new Gson();

    public BaseController(IService<T> service) {
        this.service = service;
    }

    protected abstract T mapJsonToEntity(JSONObject jsonObject);

    protected abstract String[] getValidFilters();

    protected void handleExtraGetRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
    }

    protected void handleExtraPostRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
    }

    protected void handleExtraPutRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
    }

    protected void handleExtraDeleteRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");

        switch (pathParts.length) {
            case 2:
                handleIdRequest(pathParts[1], response);
                break;
            case 0:
                if (!params.isEmpty()) {
                    handleQueryRequest(request, response);
                } else {
                    handleAllRequest(response);
                }
                break;
            default:
                handleExtraGetRequests(pathParts, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");

        if (pathParts.length == 0) {
            handlePostRequest(request, response);
        } else {
            handleExtraPostRequests(pathParts, request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = getJson(request);
            T entity = mapJsonToEntity(jsonObject);
            entity = service.update(entity);
            writeJsonResponse(response, gson.toJson(entity));
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (BadRequestException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");

        try {
            if (pathParts.length == 2) {
                handleDeleteRequest(pathParts[1], response);
            } else {
                handleExtraDeleteRequests(pathParts, request, response);
            }
        } catch (IOException e) {
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
        }
    }

    protected void setJsonResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    protected void writeJsonResponse(HttpServletResponse response, String jsonResponse) throws IOException {
        setJsonResponse(response);
        response.getWriter().write(jsonResponse);
    }

    protected void sendError(HttpServletResponse response, int statusCode, String message) {
        response.setStatus(statusCode);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("error", message);
        try {
            writeJsonResponse(response, jsonResponse.toString());
        } catch (IOException e) {
            System.out.println("Error writing error response");
        }
    }

    protected void handleIdRequest(String pathPart, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathPart);
            T entity = service.findById(id);
            writeJsonResponse(response, gson.toJson(entity));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    protected void handleQueryRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> filters = new HashMap<>();
        for (String filter : getValidFilters()) {
            if (request.getParameter(filter) != null) {
                filters.put(filter, request.getParameter(filter));
            }
        }

        if (filters.isEmpty()) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid filters");
            return;
        }

        try {
            List<T> list = service.findByFilter(filters);
            writeJsonResponse(response, gson.toJson(list));
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    protected void handleAllRequest(HttpServletResponse response) throws IOException {
        List<T> list = service.findAll();
        writeJsonResponse(response, gson.toJson(list));
    }

    protected void handlePostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = getJson(request);
            T entity = mapJsonToEntity(jsonObject);
            entity = service.add(entity);
            writeJsonResponse(response, gson.toJson(entity));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        } catch (BadRequestException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    protected void handleDeleteRequest(String pathPart, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathPart);
            service.delete(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    protected JSONObject getJson(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return new JSONObject(sb.toString());
    }
}