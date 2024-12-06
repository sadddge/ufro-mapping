package org.ufromap.core.exceptions;

import org.ufromap.core.annotations.ExceptionHandler;
import org.ufromap.core.base.BaseController;

import javax.servlet.http.HttpServletResponse;

public class ExceptionHandlers extends BaseController {
    @ExceptionHandler(BadRequestException.class)
    public void badRequest(BadRequestException e, HttpServletResponse response) {
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public void entityNotFound(EntityNotFoundException e, HttpServletResponse response) {
        sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    }
    @ExceptionHandler(UnauthorizedException.class)
    public void unauthorized(UnauthorizedException e, HttpServletResponse response) {
        sendError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
    @ExceptionHandler(InternalErrorException.class)
    public void internalError(InternalErrorException e, HttpServletResponse response) {
        sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void exception(Exception e, HttpServletResponse response) {
        sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred " + e.getMessage());
    }
}
