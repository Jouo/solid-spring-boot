package com.web.services.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.web.services.rest.utility.http.response.HttpExceptionResponse;
import com.web.services.rest.utility.http.status.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FilterResponse {

    private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;
    private HttpExceptionResponse httpExceptionResponse;
    private HttpStatus httpStatus;

    public FilterResponse(HttpExceptionResponse httpExceptionResponse) {
        this.httpExceptionResponse = httpExceptionResponse;
    }

    public void setHttpException(HttpException httpException) {
        this.httpExceptionResponse.setHttpException(httpException);
        this.httpStatus = httpException.getStatus();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        try {
            return mapper.writeValueAsString(this.httpExceptionResponse);
        }
        catch (Exception exception) {
            return null;
        }
    }
}
