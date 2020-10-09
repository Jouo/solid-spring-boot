package com.web.services.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AOPSettings {

    //      Directories
    private final String SERVICE_PATH = "execution(* com.web.services.orm.service.classes.*.";
    private final String REST_PATH = "execution(* com.web.services.rest.controller.*.";
    private final String AUTH_PATH = "execution(* com.web.services.security.AuthenticationREST.";

    //      AOP settings
    private static final String ANY_PARAMETER = "(..))";
    private static final String ANY_METHOD = "*";

    @Pointcut(AUTH_PATH + ANY_METHOD + ANY_PARAMETER)
    public void authentication() {}

    @Pointcut(SERVICE_PATH + ANY_METHOD + ANY_PARAMETER)
    public void serviceAll() {}

    @Pointcut(REST_PATH + ANY_METHOD + ANY_PARAMETER)
    public void restAll() {}
}
