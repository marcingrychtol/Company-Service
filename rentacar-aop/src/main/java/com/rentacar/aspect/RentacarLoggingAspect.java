package com.rentacar.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
public class RentacarLoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(RentacarLoggingAspect.class);

    @Pointcut("@annotation(RentacarLoggable)")
    public void executeLogging(){}

    @Before("executeLogging()")
    public void logBeforeMethodCall(JoinPoint joinPoint){
        StringBuilder message = getMessage(joinPoint);
        LOG.info("logging before method call *************************************************************************");
        LOG.info(message.toString());
    }
    @AfterReturning(value = "executeLogging()", returning = "returnValue")
    public void logAfterMethodCall(JoinPoint joinPoint, Object returnValue){
        StringBuilder message = getMessage(joinPoint);
        appendMessageForReturningValue(message, returnValue);
        LOG.info("logging after method call *************************************************************************");
        LOG.info(message.toString());
    }
    @Around(value = "executeLogging()")
    public Object logAroundMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        LOG.info("logging before proceed *************************************************************************");
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;
        LOG.info("logging after proceed *************************************************************************");
        StringBuilder message = getMessage(joinPoint);
        appendMessageForReturningValue(message, returnValue);
        message.append(", total time: ").append(totalTime).append("ms. ");
        LOG.info(message.toString());
        return returnValue;
    }

    private void appendMessageForReturningValue(StringBuilder message, Object returnValue) {
        if(returnValue instanceof Collection){
            message.append(", returning: ").append(((Collection)returnValue).size()).append(" instance(s)");
        }else{
            message.append(", returning: ").append(returnValue.toString());
        }
    }

    private StringBuilder getMessage(JoinPoint joinPoint){
        StringBuilder message = new StringBuilder();
        message.append(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (null!=args && args.length>0){
            message.append(" args=[ | ");
            Arrays.asList(args).forEach(arg->{
                message.append(arg).append(" | ");
            });
            message.append("]  ************************************************************************* ");
        }
        return message;
    }
}
