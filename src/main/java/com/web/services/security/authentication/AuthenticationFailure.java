package com.web.services.security.authentication;

import com.web.services.security.authentication.listener.FailureListener;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFailure
        implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private FailureListener failureListener;
    private HttpServletRequest request;

    public AuthenticationFailure(FailureListener failureListener, HttpServletRequest request) {
        this.failureListener = failureListener;
        this.request = request;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        failureListener.loginFailed(request.getRemoteAddr());
    }
}
