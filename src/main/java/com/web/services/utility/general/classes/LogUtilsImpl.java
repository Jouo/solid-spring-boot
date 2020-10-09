package com.web.services.utility.general.classes;

import com.web.services.utility.general.interfaces.LogUtils;
import org.aspectj.lang.JoinPoint;

public class LogUtilsImpl implements LogUtils {

    private final int USERNAME_LENGTH = 12;
    private final int AOP_LENGTH = 9;
    private final int METHOD_LENGTH = 8;

    private String username;
    private String aopType;
    private String method;
    private String arguments;

    public LogUtilsImpl(String username, String aopType, JoinPoint joinPoint) {
        this.username = username;
        this.aopType = aopType;
        this.method = JoinPointUtils.getMethodName(joinPoint);
        this.arguments = JoinPointUtils.getArguments(joinPoint);
    }

    @Override
    public String getMessage() {
        if (this.arguments != null) {
            return getFormattedMessage() + arguments;
        }
        return getFormattedMessage();
    }

    @Override
    public String getMessage(String message) {
        return getFormattedMessage() + message;
    }

    private String getFormattedMessage() {
        String username = indent(this.username, USERNAME_LENGTH);
        String AOP = indent(this.aopType, AOP_LENGTH);
        String method = indent(this.method, METHOD_LENGTH);
        return username + AOP + method;
    }

    private String indent(String text, int minimumLength) {
        if (minimumLength > text.length()) {
            int difference = minimumLength - text.length();
            text += " ".repeat(difference);
            return text;
        }
        return text + "   ";
    }
}
