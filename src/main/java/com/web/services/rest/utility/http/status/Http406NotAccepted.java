package com.web.services.rest.utility.http.status;

import com.web.services.Setting;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Http406NotAccepted extends HttpException {

    public Http406NotAccepted() {
        this(Setting.HTTP_406_MESSAGE);
    }

    public Http406NotAccepted(String description) {
        super(HttpStatus.NOT_ACCEPTABLE, description);
    }
}
