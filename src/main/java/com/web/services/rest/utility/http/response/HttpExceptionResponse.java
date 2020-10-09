package com.web.services.rest.utility.http.response;

import com.web.services.rest.utility.http.status.HttpException;

public interface HttpExceptionResponse {

    void setHttpException(HttpException httpException);
}
