package com.web.services.rest.utility.http.status;

import com.web.services.Setting;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Http404NotFound extends HttpException {

    public Http404NotFound() {
        this(Setting.HTTP_404_MESSAGE);
    }

    public Http404NotFound(String description) {
        super(HttpStatus.NOT_FOUND, description);
    }
}
