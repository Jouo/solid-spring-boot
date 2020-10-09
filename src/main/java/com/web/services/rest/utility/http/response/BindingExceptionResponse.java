package com.web.services.rest.utility.http.response;

import org.springframework.validation.BindingResult;

public interface BindingExceptionResponse {

    void setBindingResult(BindingResult bindingResult);
}
