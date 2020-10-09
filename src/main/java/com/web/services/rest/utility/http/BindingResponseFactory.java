package com.web.services.rest.utility.http;

import com.web.services.rest.utility.http.response.BindingExceptionResponse;
import com.web.services.rest.utility.http.response.HttpExceptionResponse;
import com.web.services.rest.utility.http.status.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class BindingResponseFactory {

    private final BindingExceptionResponse bindingExceptionResponse;

    public BindingResponseFactory(BindingExceptionResponse bindingExceptionResponse) {
        this.bindingExceptionResponse = bindingExceptionResponse;
    }

    public ResponseEntity<?> response(BindingResult bindingResult) {
        this.bindingExceptionResponse.setBindingResult(bindingResult);
        return new ResponseEntity<>(this.bindingExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
