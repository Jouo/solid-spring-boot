package com.web.services.rest.utility.http.status;

import com.web.services.Setting;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Http401Unauthorized extends HttpException {

    public Http401Unauthorized() {
        this(Setting.HTTP_401_MESSAGE);
    }

    public Http401Unauthorized(String description) {
        super(HttpStatus.UNAUTHORIZED, description);
    }
}
