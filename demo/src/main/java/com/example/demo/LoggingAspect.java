package com.example.demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.demo.UserController.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        logger.info("Entering in Method :  " + joinPoint.getSignature().getName());
        logger.info("Arguments :  " + joinPoint.getArgs());
        
        Object result = joinPoint.proceed();
        
        long elapsedTime = System.currentTimeMillis() - start;
        logger.info("Exiting from Method : " + joinPoint.getSignature().getName());
        logger.info("Method Return value : " + result);
        logger.info("Method execution time: " + elapsedTime + " milliseconds.");
        
        return result;
    }
}
