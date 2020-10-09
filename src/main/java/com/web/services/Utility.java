package com.web.services;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Utility {

    public static String dateFormat(Long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Setting.ENTITY_DATE_PATTERN);
        Instant time = Instant.ofEpochSecond(date);
        return dateFormat.format(Date.from(time));
    }

    public static String arrayFormat(Long id, Object object) {
        return String.format(
                "(%d, %d)", id, object);
    }
}
