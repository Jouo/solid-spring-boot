package com.web.services.rest.utility.http.status;

import com.web.services.Setting;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Http200Ok extends HttpException {

    public Http200Ok() {
        this(Setting.HTTP_200_MESSAGE);
    }

    public Http200Ok(String description) {
        super(HttpStatus.ACCEPTED, description);
    }
}
