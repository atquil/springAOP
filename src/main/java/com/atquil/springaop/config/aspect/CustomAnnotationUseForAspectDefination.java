package com.atquil.springaop.config.aspect;

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
 * ✅ Aspect that intercepts methods annotated with @MyCustomAnnotationForPaymentValidation
 * and applies various types of advice for logging and validation.
 */
@Aspect
@Component
@Slf4j
public class CustomAnnotationUseForAspectDefination {

    // 🎯 Named Pointcut: Matches methods annotated with @MyCustomAnnotationForPaymentValidation
    @Pointcut("@annotation(com.atquil.springaop.config.annotation.MyCustomAnnotationForPaymentValidation)")
    private void paymentValidationPointcut() {}

    // 1️⃣ @Before — Runs before method execution
    @Before("paymentValidationPointcut()")
    public void beforeValidation(JoinPoint jp) {
        PaymentRequestDto dto = (PaymentRequestDto) jp.getArgs()[0];
        log.info("[Custom - @Before] {} - isPaymentValid:{} - isPaymentDone:{}",
                jp.getSignature().toShortString(), dto.isPaymentValid(), dto.isPaymentDone());
    }

    // 2️⃣ @After — Runs after method execution (success or failure)
    @After("paymentValidationPointcut()")
    public void afterValidation(JoinPoint jp) {
        PaymentRequestDto dto = (PaymentRequestDto) jp.getArgs()[0];
        log.info("[Custom - @After] {} - isPaymentDone:{}",
                jp.getSignature().toShortString(), dto.isPaymentDone());
    }

    // 3️⃣ @AfterReturning — Runs after successful execution
    @AfterReturning(
            pointcut = "paymentValidationPointcut()",
            returning = "result"
    )
    public void afterReturning(JoinPoint jp, Object result) {
        log.info("[Custom - @AfterReturning] {} returned: {}",
                jp.getSignature().toShortString(), result);
    }

    // 4️⃣ @AfterThrowing — Runs if method throws an exception
    @AfterThrowing(
            pointcut = "paymentValidationPointcut()",
            throwing = "exception"
    )
    public void afterThrowing(JoinPoint jp, Throwable exception) {
        log.error("[Custom - @AfterThrowing] {} threw: {}",
                jp.getSignature().toShortString(), exception.getMessage(), exception);
    }

    // 5️⃣ @Around — Wraps method execution (before & after)
    @Around("paymentValidationPointcut()")
    public Object aroundValidation(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        PaymentRequestDto dto = (pjp.getArgs().length > 0 && pjp.getArgs()[0] instanceof PaymentRequestDto)
                ? (PaymentRequestDto) pjp.getArgs()[0] : null;

        String methodName = pjp.getSignature().toShortString();
        try {
            log.info("[Custom - @Around] Entering {} - isPaymentDone:{}", methodName, dto != null ? dto.isPaymentDone() : "N/A");
            Object result = pjp.proceed(); // ✅ Proceed with method execution
            log.info("Custom - [@Around] Exiting {}", methodName);
            return result;
        } catch (Throwable e) {
            log.warn("[Custom - @Around] Exception in {}", methodName);
            throw e;
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("[Custom - @Around]{} executed in {} ms", methodName, duration);
        }
    }
}
