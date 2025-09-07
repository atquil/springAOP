package com.atquil.springaop.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
public class TransactionAspect {

    @Around("@within(org.springframework.stereotype.Service)")
    public Object manageTransaction(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        log.info("Transaction start: " + pjp.getSignature().toShortString());
        log.info("Entering: {}.{}() with arguments: {}", className, methodName, args);
        try {
            Object result = pjp.proceed();
            log.info("Transaction commit: " + pjp.getSignature().toShortString());
            return result;
        } catch (Exception ex) {
            log.error("Transaction rollback: " + ex.getMessage());
            throw ex;
        }
    }
}
