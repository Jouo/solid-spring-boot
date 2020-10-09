package com.web.services.rest.utility.http.status;

import com.web.services.Setting;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Http400BadRequest extends HttpException {

    public Http400BadRequest() {
        this(Setting.HTTP_400_MESSAGE);
    }

    public Http400BadRequest(String description) {
        super(HttpStatus.BAD_REQUEST, description);
    }
}
