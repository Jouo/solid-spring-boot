package com.web.services.utility.general.classes;

import org.aspectj.lang.JoinPoint;

import java.util.ArrayList;
import java.util.List;

public class JoinPointUtils {

    public static String getArguments(JoinPoint joinPoint) {
        List<String> arguments = new ArrayList<>();

        for (Object parameter : joinPoint.getArgs()) {
            if (parameter != null) {
                arguments.add(parameter.toString());
            }
        }

        if (!arguments.isEmpty()) {
            return arguments.toString()
                    .replaceAll("\\[", "")
                    .replaceAll("\\]","");
        }
        return null;
    }

    public static String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    public static String getServiceName(JoinPoint joinPoint) {
        String serviceName = joinPoint.getTarget().getClass().getSimpleName();
        return serviceName.replace("Impl", "");
    }
}
