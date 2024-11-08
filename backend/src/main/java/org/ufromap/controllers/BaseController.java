package org.ufromap.controllers;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseController<T> extends HttpServlet {
    protected abstract T processPost(T entity) throws BadRequestException;
    protected abstract T processPut(T entity) throws EntityNotFoundException;
    protected abstract void processDelete(int id) throws EntityNotFoundException;
    protected abstract T mapJsonToEntity(JSONObject jsonObject);
    protected void handlePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = getJson(request);
            T entity = mapJsonToEntity(jsonObject);
            entity = processPost(entity);
            writeJsonResponse(response, new Gson().toJson(entity));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        } catch (BadRequestException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    protected void handlePut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = getJson(request);
            T entity = mapJsonToEntity(jsonObject);
            entity = processPut(entity);
            writeJsonResponse(response, new Gson().toJson(entity));
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    protected void handleDelete(HttpServletRequest request, HttpServletResponse response){
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");

        if (pathParts.length != 2) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }

        try {
            int id = Integer.parseInt(pathParts[1]);
            processDelete(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
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

    protected void handleIdRequest(String pathPart, HttpServletResponse response, Function<Integer, T> findById)  throws IOException {
        try {
            int id = Integer.parseInt(pathPart);
            T entity = findById.apply(id);
            writeJsonResponse(response, new Gson().toJson(entity));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    protected void handleAllRequest(HttpServletResponse response, Supplier<List<T>> findAll) throws IOException {
        List<T> list = findAll.get();
        writeJsonResponse(response, new Gson().toJson(list));
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
