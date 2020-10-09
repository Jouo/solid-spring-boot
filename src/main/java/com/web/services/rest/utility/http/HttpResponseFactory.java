package com.web.services.rest.utility.http;

import com.web.services.rest.utility.http.response.HttpExceptionResponse;
import com.web.services.rest.utility.http.status.Http400BadRequest;
import com.web.services.rest.utility.http.status.HttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HttpResponseFactory {

    private final HttpExceptionResponse httpExceptionResponse;

    public HttpResponseFactory(HttpExceptionResponse httpExceptionResponse) {
        this.httpExceptionResponse = httpExceptionResponse;
    }

    public ResponseEntity<?> exceptionResponse(HttpException httpException) {
        this.httpExceptionResponse.setHttpException(httpException);
        return new ResponseEntity<>(this.httpExceptionResponse, httpException.getStatus());
    }

    public ResponseEntity<?> response(Object object) {
        if (object != null) {
            return ResponseEntity.ok(object);
        }
        return exceptionResponse(new Http400BadRequest("Returned null"));
    }
}
