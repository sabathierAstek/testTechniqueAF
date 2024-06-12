package com.example.demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.demo.UserController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering in Method :  " + joinPoint.getSignature().getName());
        logger.info("Arguments :  " + joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.example.demo.UserController.*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("Method Return value : " + result);
    }
}
