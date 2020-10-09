package com.web.services.rest.utility.http.status;

import com.web.services.Setting;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Http403Forbidden extends HttpException {

    public Http403Forbidden() {
        this(Setting.HTTP_403_MESSAGE);
    }

    public Http403Forbidden(String description) {
        super(HttpStatus.FORBIDDEN, description);
    }
}
