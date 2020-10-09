package com.web.services.rest.utility.http.status;

import com.web.services.Setting;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Http405MethodNotAllowed extends HttpException {

    public Http405MethodNotAllowed() {
        this(Setting.HTTP_405_MESSAGE);
    }

    public Http405MethodNotAllowed(String description) {
        super(HttpStatus.METHOD_NOT_ALLOWED, description);
    }
}
