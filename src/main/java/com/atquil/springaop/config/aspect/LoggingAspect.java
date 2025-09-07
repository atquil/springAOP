package com.atquil.springaop.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author atquil
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {
    //Objective: Log entry and exit of all service methods.
    @Pointcut("execution(* com.atquil.springaop.service.*.*(..))")
    private void serviceLayer() {}

    @Before("serviceLayer()")
    public void logBefore(JoinPoint jp) {
        log.info("Entering: " + jp.getSignature().toShortString());
    }

    @After("serviceLayer()")
    public void logAfter(JoinPoint jp) {
        log.info("Exiting: " + jp.getSignature().toShortString());
    }
}
