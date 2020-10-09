package com.web.services.aop;

import com.web.services.Setting;
import com.web.services.utility.general.classes.JoinPointUtils;
import com.web.services.utility.general.classes.LogUtilsImpl;
import com.web.services.utility.general.interfaces.LogUtils;
import com.web.services.utility.orm.interfaces.UserUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class AOP {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String OR = " || ";
    private UserUtils userUtils;

    public AOP(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    //      DEBUG & WARN = Console output
    //      INFO & ERROR = File output


    // 23 Oct 2020   07:30:00   ProductService   developer   Throw   save   id: 26, pizza, etc...
    // Followed by the throwable stack trace
    @AfterThrowing(value = Pointcut.SERVICES + OR + Pointcut.REST + OR + Pointcut.AUTHENTICATION,
            throwing = "exception")
    public void afterCRUD(JoinPoint joinPoint, Throwable exception) {

        LogUtils logUtils = new LogUtilsImpl(this.userUtils.getUsername(), LogUtils.THROW, joinPoint);
        MDC.put(Setting.AOP_LOGBACK_KEY, JoinPointUtils.getServiceName(joinPoint));

        logger.warn(logUtils.getMessage(), exception);
        logger.error(logUtils.getMessage(), exception);
    }

    // 23 Oct 2020   07:30:00   ProductService   developer   Before   get   42
    @Before(Pointcut.SERVICES)
    public void beforeServiceCRUD(JoinPoint joinPoint) {

        LogUtils logUtils = new LogUtilsImpl(this.userUtils.getUsername(), LogUtils.BEFORE, joinPoint);
        MDC.put(Setting.AOP_LOGBACK_KEY, JoinPointUtils.getServiceName(joinPoint));

        logger.debug(logUtils.getMessage());
        logger.info(logUtils.getMessage());
    }

    // 23 Oct 2020   07:30:00   ProductService   developer   After   get   id: 42, beer, etc...
    @AfterReturning(value = Pointcut.SERVICES + OR + Pointcut.REST + OR + Pointcut.AUTHENTICATION,
            returning = "object")
    public void beforeServiceCRUD(JoinPoint joinPoint, Object object) {

        LogUtils logUtils = new LogUtilsImpl(this.userUtils.getUsername(), LogUtils.AFTER, joinPoint);
        MDC.put(Setting.AOP_LOGBACK_KEY, JoinPointUtils.getServiceName(joinPoint));

        String message;

        if (object instanceof ResponseEntity) {
            ResponseEntity response = (ResponseEntity) object;
            object = response.getBody();
        }

        if (object instanceof List) { message = logList((List) object); }
        else { message = logObject(object); }

        logger.debug(logUtils.getMessage(message));
        logger.info(logUtils.getMessage(message));
    }

    private String logObject(Object object) {
        if (object != null) {
            return object.toString();
        }
        return Setting.AOP_NULL_ITEM;
    }

    private String logList(List array) {
        if ((array != null) && !array.isEmpty()) {
            return "Returned a list of [" + array.size() + "] objects";
        }
        return Setting.AOP_EMPTY_LIST;
    }
}
