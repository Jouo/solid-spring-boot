package com.web.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component("setting")
public class Setting {

    public static String AOP_LOGBACK_KEY;
    public static String AOP_NULL_ITEM;
    public static String AOP_EMPTY_LIST;
    public static Integer AOP_BLANK_SPACES;

    public static String RESPONSE_DATE_PATTERN;
    public static String ENTITY_DATE_PATTERN;

    public static Integer AUTH_ATTEMPTS;
    public static Integer AUTH_BAN_TIME;
    public static String AUTH_CREDENTIALS_MESSAGE;
    public static String AUTH_ACCOUNT_BAN_MESSAGE;
    public static String AUTH_LOCKED_MESSAGE;
    public static String AUTH_IP_BAN_MESSAGE;
    public static String AUTH_USER_NOT_FOUND_MESSAGE;
    public static String AUTH_ROLE_NOT_FOUND_MESSAGE;

    public static String JWT_SECRET;
    public static Integer JWT_EXPIRATION_HOURS;
    public static String JWT_MALFORMED;
    public static String JWT_EXPIRED;
    public static String JWT_INVALID_HEADER;

    public static String HTTP_200_MESSAGE;
    public static String HTTP_400_MESSAGE;
    public static String HTTP_401_MESSAGE;
    public static String HTTP_403_MESSAGE;
    public static String HTTP_404_MESSAGE;
    public static String HTTP_405_MESSAGE;
    public static String HTTP_406_MESSAGE;
    public static String HTTP_500_MESSAGE;


    @Value("${aop.logback.key}")
    public void setAopLogbackKey(String aopLogbackKey) {
        AOP_LOGBACK_KEY = aopLogbackKey;
    }

    @Value("${aop.null.message}")
    public void setAopNullItem(String aopNullItem) {
        AOP_NULL_ITEM = aopNullItem;
    }

    @Value("${aop.empty.message}")
    public void setAopEmptyList(String aopEmptyList) {
        AOP_EMPTY_LIST = aopEmptyList;
    }

    @Value("${aop.default.spaces.between.fields}")
    public void setAopBlankSpaces(Integer aopBlankSpaces) {
        AOP_BLANK_SPACES = aopBlankSpaces;
    }

    @Value("${http.response.date.pattern}")
    public void setResponseDatePattern(String responseDatePattern) {
        RESPONSE_DATE_PATTERN = responseDatePattern;
    }

    @Value("${orm.entity.date.pattern}")
    public void setEntityDatePattern(String entityDatePattern) {
        ENTITY_DATE_PATTERN = entityDatePattern;
    }

    @Value("${http.200.message}")
    public void setHttp200Message(String http200Message) {
        HTTP_200_MESSAGE = http200Message;
    }

    @Value("${http.400.message}")
    public void setHttp400Message(String http400Message) {
        HTTP_400_MESSAGE = http400Message;
    }

    @Value("${http.401.message}")
    public void setHttp401Message(String http401Message) {
        HTTP_401_MESSAGE = http401Message;
    }

    @Value("${http.403.message}")
    public void setHttp403Message(String http403Message) {
        HTTP_403_MESSAGE = http403Message;
    }

    @Value("${http.404.message}")
    public void setHttp404Message(String http404Message) {
        HTTP_404_MESSAGE = http404Message;
    }

    @Value("${http.405.message}")
    public void setHttp405Message(String http405Message) {
        HTTP_405_MESSAGE = http405Message;
    }

    @Value("${http.406.message}")
    public void setHttp406Message(String http406Message) {
        HTTP_406_MESSAGE = http406Message;
    }

    @Value("${http.500.message}")
    public void setHttp500Message(String http500Message) {
        HTTP_500_MESSAGE = http500Message;
    }

    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }

    @Value("${jwt.expiration.hours}")
    public void setJwtExpirationHours(Integer jwtExpirationHours) {
        JWT_EXPIRATION_HOURS = jwtExpirationHours;
    }

    @Value("${jwt.filter.malformed.message}")
    public void setJwtMalformed(String jwtMalformed) {
        JWT_MALFORMED = jwtMalformed;
    }

    @Value("${jwt.filter.expired.message}")
    public void setJwtExpired(String jwtExpired) {
        JWT_EXPIRED = jwtExpired;
    }

    @Value("${jwt.filter.invalid.message}")
    public void setJwtInvalidHeader(String jwtInvalidHeader) {
        JWT_INVALID_HEADER = jwtInvalidHeader;
    }

    @Value("${auth.login.attempts}")
    public void setAuthAttempts(Integer authAttempts) {
        AUTH_ATTEMPTS = authAttempts;
    }

    @Value("${auth.login.ban.minutes}")
    public void setAuthBanTime(Integer authBanTime) {
        AUTH_BAN_TIME = authBanTime;
    }

    @Value("${auth.credentials.message}")
    public void setAuthCredentialsMessage(String authCredentialsMessage) {
        AUTH_CREDENTIALS_MESSAGE = authCredentialsMessage;
    }

    @Value("${auth.account.ban.message}")
    public void setAuthAccountBanMessage(String authAccountBanMessage) {
        AUTH_ACCOUNT_BAN_MESSAGE = authAccountBanMessage;
    }

    @Value("${auth.account.locked.message}")
    public void setAuthLockedMessage(String authLockedMessage) {
        AUTH_LOCKED_MESSAGE = authLockedMessage;
    }

    @Value("${auth.ip.ban.message}")
    public void setAuthIpBanMessage(String authIpBanMessage) {
        AUTH_IP_BAN_MESSAGE = authIpBanMessage;
    }

    @Value("${auth.user.not.found.message}")
    public void setAuthUserNotFoundMessage(String authUserNotFoundMessage) {
        AUTH_USER_NOT_FOUND_MESSAGE = authUserNotFoundMessage;
    }

    @Value("${auth.role.not.found.message}")
    public void setAuthRoleNotFoundMessage(String authRoleNotFoundMessage) {
        AUTH_ROLE_NOT_FOUND_MESSAGE = authRoleNotFoundMessage;
    }
}
