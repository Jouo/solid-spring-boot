package com.web.services.rest.utility.handler;

import com.web.services.rest.utility.http.HttpResponseFactory;
import com.web.services.rest.utility.http.status.Http404NotFound;
import com.web.services.rest.utility.http.status.Http500InternalServerError;
import com.web.services.rest.utility.http.status.HttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class HttpRequestHandler {

    private final HttpResponseFactory factory;

    public HttpRequestHandler(HttpResponseFactory factory) {
        this.factory = factory;
    }

    @ExceptionHandler
    public ResponseEntity<?> responseEntity(NoHandlerFoundException exception) {
        return this.factory.exceptionResponse(new Http404NotFound(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> responseEntity(HttpException httpException) {
        return this.factory.exceptionResponse(httpException);
    }

    @ExceptionHandler
    public ResponseEntity<?> Http405(HttpRequestMethodNotSupportedException exception) {
        return this.factory.exceptionResponse(new Http404NotFound(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> runtime(RuntimeException exception) {
        return this.factory.exceptionResponse(new Http500InternalServerError(exception.getMessage()));
    }
}