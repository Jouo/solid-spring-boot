package com.web.services.utility.general.interfaces;

public interface LogUtils {

    String BEFORE = "Before";
    String AFTER = "After";
    String THROW = "Throw";

    String getMessage();

    String getMessage(String message);
}
