package com.web.services.security.authentication.listener;

public interface FailureListener {

    void save(String ip);

    void loginSucceeded(String ip);

    void loginFailed(String ip);

    boolean isBlocked(String ip);
}
