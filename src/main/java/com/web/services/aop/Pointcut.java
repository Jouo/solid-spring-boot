package com.web.services.aop;

public class Pointcut {

    private static final String PACKAGE = "com.web.services.aop.AOPSettings.";

    public static final String SERVICES = PACKAGE + "serviceAll()";
    public static final String REST = PACKAGE + "restAll()";
    public static final String AUTHENTICATION = PACKAGE + "authentication()";
}
