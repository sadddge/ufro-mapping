package org.ufromap.controllers;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.services.ICrudService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CrudController<DTO, RequestDTO, Entity> extends BaseController{
    protected Gson gson = new Gson();
    protected final ICrudService<DTO, RequestDTO, Entity> service;
    public CrudController(ICrudService<DTO, RequestDTO, Entity> service) {
        this.service = service;
    }
    protected abstract RequestDTO mapJsonToEntity(JSONObject jsonObject);
    protected abstract String[] getValidFilters();

    protected void handleExtraGetRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
    }

    protected void handleExtraPostRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
    }

    protected void handleExtraPutRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response){
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
    }

    protected void handleExtraDeleteRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("PATCH")){
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
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
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");

        if (pathParts.length == 2) {
            handlePutRequest(pathParts[1], request, response);
        } else {
            handleExtraPutRequests(pathParts, request, response);
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

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");
        try {
            int id = Integer.parseInt(pathParts[1]);
            JSONObject jsonObject = getJson(request);
            DTO updatedEntity = service.patch(id, jsonObject);
            writeJsonResponse(response, gson.toJson(updatedEntity));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (BadRequestException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
        }
    }

    protected void handleIdRequest(String pathPart, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathPart);
            DTO entity = service.findById(id);
            writeJsonResponse(response, gson.toJson(entity));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
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
            List<DTO> list = service.findByFilter(filters);
            writeJsonResponse(response, gson.toJson(list));
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    protected void handleAllRequest(HttpServletResponse response) throws IOException {
        List<DTO> list = service.findAll();
        writeJsonResponse(response, gson.toJson(list));
    }

    protected void handlePostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = getJson(request);
            RequestDTO entity = mapJsonToEntity(jsonObject);
            DTO responseEntity = service.add(entity);
            writeJsonResponse(response, gson.toJson(responseEntity));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        } catch (BadRequestException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    protected void handlePutRequest(String pathPart, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathPart);
            JSONObject jsonObject = getJson(request);
            RequestDTO entity = mapJsonToEntity(jsonObject);
            DTO responseDTO = service.update(id, entity);
            writeJsonResponse(response, gson.toJson(responseDTO));
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (BadRequestException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        }
    }

    protected void handleDeleteRequest(String pathPart, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathPart);
            service.delete(id);
            writeJsonResponse(response, new JSONObject().toString());
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
