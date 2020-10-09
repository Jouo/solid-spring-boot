package com.web.services.security.authentication;

import com.web.services.security.authentication.listener.FailureListener;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationSuccess
        implements ApplicationListener<AuthenticationSuccessEvent> {

    private FailureListener failureListener;
    private HttpServletRequest request;

    public AuthenticationSuccess(FailureListener failureListener, HttpServletRequest request) {
        this.failureListener = failureListener;
        this.request = request;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        failureListener.loginSucceeded(request.getRemoteAddr());
    }
}
