package org.ufromap.core.base;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.json.JSONObject;
@Log
public abstract class BaseController extends HttpServlet {
    protected void setJsonResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    protected void sendObject(HttpServletResponse response, Object object) throws IOException {
        setJsonResponse(response);
        response.getWriter().write(new Gson().toJson(object));
    }

    protected void sendMessage(HttpServletResponse response, String message) throws IOException {
        setJsonResponse(response);
        response.getWriter().write(new JSONObject().put("message", message).toString());
    }

    protected void sendError(HttpServletResponse response, int statusCode, String message) {
        response.setStatus(statusCode);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("error", message);
        try {
            sendObject(response, jsonResponse);
        } catch (IOException e) {
            log.log(java.util.logging.Level.SEVERE, "Error sending error response", e);
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