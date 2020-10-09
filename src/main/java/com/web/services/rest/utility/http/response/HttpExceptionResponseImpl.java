package com.web.services.rest.utility.http.response;

import com.web.services.Setting;
import com.web.services.rest.utility.http.status.HttpException;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Component
public class HttpExceptionResponseImpl implements HttpExceptionResponse {

    private String date;
    private Integer status;
    private String error;
    private String description;

    @Override
    public void setHttpException(HttpException httpException) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Setting.RESPONSE_DATE_PATTERN);
        this.date = dateFormat.format(Date.from(Instant.now()));
        this.status = httpException.getStatus().value();
        this.error = httpException.getError();
        this.description = httpException.getDescription();
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("status", status).add("error", error).add("description", description);
        return formatter.format();
    }

    public String getDate() {
        return date;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}
