package org.ufromap.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.json.JSONObject;

public abstract class BaseController extends HttpServlet {
    protected void setJsonResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    protected void writeJsonResponse(HttpServletResponse response, Object object) throws IOException {
        setJsonResponse(response);
        response.getWriter().write(new Gson().toJson(object));
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