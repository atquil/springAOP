package com.atquil.springaop.explanations;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author atquil
 */
@Aspect //
@Component // To Register the bean
@Slf4j
public class _1_ExplainAspect {

    // Logs before any method in the service package is executed
    @Before("execution(* com.atquil.springaop.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString(); // Extract method name
        log.info("🔍 [BEFORE] Executing: {}", methodName);
    }

    // Logs after any method in the service package has completed
    @After("execution(* com.atquil.springaop.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString(); // Extract method name
        log.info("✅ [AFTER] Completed: {}", methodName);
    }

    // Profiles execution time of service methods using Around advice
    @Around("execution(* com.atquil.springaop.service.*.*(..))")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString(); // Extract method name
        long startTime = System.currentTimeMillis(); // Start timer
        log.debug("⏱️ [PROFILE] Starting: {}", methodName);
        try {
            return joinPoint.proceed(); // Proceed with method execution
        } finally {
            long duration = System.currentTimeMillis() - startTime; // Calculate duration
            log.debug("⏱️ [PROFILE] Finished: {} in {} ms", methodName, duration);
        }
    }

    // Logs the returned value after successful method execution
    @AfterReturning(
            pointcut = "execution(* com.atquil.springaop.service.*.*(..))",
            returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString(); // Extract method name
        log.info("📦 [RETURN] Method {} returned: {}", methodName, result);
    }

    // Logs exception details if a method throws an error
    @AfterThrowing(
            pointcut = "execution(* com.atquil.springaop.service.*.*(..))",
            throwing = "exception"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().toShortString(); // Extract method name
        log.error("💥 [EXCEPTION] Method {} threw: {}", methodName, exception.getMessage(), exception);
    }
}
