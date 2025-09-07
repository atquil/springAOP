package com.atquil.springaop.explanations;

import com.atquil.springaop.dto.PaymentRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author atquil
 */
/**
 * ✅ This aspect demonstrates all major types of Spring AOP advice:
 * @Before, @After, @AfterReturning, @AfterThrowing, and @Around.
 * It uses two pointcuts: one for custom annotation, one for controller classes.
 */
@Aspect
@Component
@Slf4j
public class _3_ExplainAdvice {

    // 🔹 Pointcut 1: Matches methods annotated with @MyCustomAnnotationForPaymentValidation
    @Pointcut("@annotation(com.atquil.springaop.config.annotation.MyCustomAnnotationForPaymentValidation)")
    public void customValidationMethods() {
        // Use case: Apply validation or authorization logic on annotated methods
    }

    // 🔹 Pointcut 2: Matches all methods in classes under controller package
    @Pointcut("within(com.atquil.springaop.controller..*)")
    public void applyOnController() {
        // Use case: Apply logging or monitoring across all controller endpoints
    }

    // 1️⃣ @Before Advice — Runs *before* the method execution
    @Before("applyOnController()")
    public void beforeCallingController(JoinPoint jp) {
        log.info("[@Before] Method {} is about to execute", jp.getSignature().toShortString());
    }

    // 2️⃣ @After Advice — Runs *after* method execution (regardless of outcome)
    @After("customValidationMethods() || applyOnController()")
    public void afterCallingController(JoinPoint jp) {
        log.info("[@After] Method {} has completed execution", jp.getSignature().toShortString());
    }

    // 3️⃣ @AfterReturning Advice — Runs *after* successful method execution
    @AfterReturning(
            pointcut = "customValidationMethods() || applyOnController()",
            returning = "result" // Must match method parameter name
    )
    public void logAfterReturning(JoinPoint jp, Object result) {
        log.info("[@AfterReturning] Method {} returned: {}", jp.getSignature().toShortString(), result);
    }

    // 4️⃣ @AfterThrowing Advice — Runs *after* method throws an exception
    @AfterThrowing(
            pointcut = "@annotation(com.atquil.springaop.config.annotation.MyCustomAnnotationForPaymentValidation)",
            throwing = "exception" // Must match method parameter name
    )
    public void logAfterThrowing(JoinPoint jp, Throwable exception) {
        log.error("[@AfterThrowing] Method {} threw exception: {}", jp.getSignature().toShortString(), exception.getMessage(), exception);
    }

    // 5️⃣ @Around Advice — Wraps method execution (before & after)
    @Around("customValidationMethods() || applyOnController()")
    public Object applyAuthorization(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().toShortString();
        log.info("[@Around] Entering method {}", methodName);

        // ✅ Example condition check before proceeding
        boolean isAuthorized = true; // Replace with actual logic
        if (isAuthorized) {
            log.info("[@Around] Authorization passed for {}", methodName);
            Object result = pjp.proceed(); // Proceed with method execution
            log.info("[@Around] Exiting method {}", methodName);
            return result;
        } else {
            log.warn("[@Around] Authorization failed for {}", methodName);
            return null; // Block execution or return fallback
        }
    }
}