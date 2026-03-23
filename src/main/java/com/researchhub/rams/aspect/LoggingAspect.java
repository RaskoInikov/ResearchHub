package com.researchhub.rams.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.researchhub.rams.service..*(..))")
    public void serviceLayer() {
        
    }

    @Around("serviceLayer()")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();

        log.info("Enter: {}.{}() with args = {}",
                className,
                methodName,
                Arrays.toString(args)
        );

        try {
            Object result = joinPoint.proceed();

            long time = System.currentTimeMillis() - start;

            log.info("Exit: {}.{}() - {} ms",
                    className,
                    methodName,
                    time
            );

            return result;

        } catch (Exception ex) {

            long time = System.currentTimeMillis() - start;

            log.error("Exception in {}.{}() - {} ms - message: {}",
                    className,
                    methodName,
                    time,
                    ex.getMessage()
            );

            throw ex;
        }
    }
}