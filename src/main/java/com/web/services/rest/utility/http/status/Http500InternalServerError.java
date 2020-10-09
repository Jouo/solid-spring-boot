package com.web.services.rest.utility.http.status;

import com.web.services.Setting;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Http500InternalServerError extends HttpException {

    public Http500InternalServerError() {
        this(Setting.HTTP_500_MESSAGE);
    }

    public Http500InternalServerError(String description) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, description);
    }
}
