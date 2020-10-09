package com.web.services.rest.utility.http.status;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {

    protected final HttpStatus httpStatus;
    protected final String error;
    protected final String description;

    public HttpException(HttpStatus status, String description) {
        super(description);
        this.httpStatus = status;
        this.error = status.getReasonPhrase();
        this.description = description;
    }

    public HttpStatus getStatus() {
        return this.httpStatus;
    }

    public String getError() {
        return this.error;
    }

    public String getDescription() {
        return this.description;
    }
}
