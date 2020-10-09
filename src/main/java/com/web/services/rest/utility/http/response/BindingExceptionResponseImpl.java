package com.web.services.rest.utility.http.response;

import com.web.services.Setting;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BindingExceptionResponseImpl implements BindingExceptionResponse {

    private String date;
    private List<BindingError> errors;

    public BindingExceptionResponseImpl() {}

    @Override
    public void setBindingResult(BindingResult bindingResult) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Setting.RESPONSE_DATE_PATTERN);
        this.date = dateFormat.format(Date.from(Instant.now()));
        this.errors = errorsFrom(bindingResult);
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("errors", errors);
        return formatter.format();
    }

    private List<BindingError> errorsFrom(BindingResult bindingResult) {
        List<FieldError> errorsThrown = bindingResult.getFieldErrors();
        List<BindingError> errorsFormatted = new ArrayList<>();

        for (FieldError error : errorsThrown) {
            errorsFormatted.add(new BindingError(
                    error.getField(),
                    error.getRejectedValue(),
                    error.getDefaultMessage()
            ));
        }
        return errorsFormatted;
    }

    public String getDate() {
        return date;
    }

    public List<BindingError> getErrors() {
        return errors;
    }

    static class BindingError {

        private String field;
        private Object value;
        private String message;

        public BindingError(String field, Object value, String message) {
            this.field = field;
            this.value = value;
            this.message = message;
        }

        @Override
        public String toString() {
            return String.format("(%s, %s, %s)", field, value, message);
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }
    }
}
