package com.atquil.springaop.explanations;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author atquil
 */
@Aspect
@Component
public class _2_ExplainPointCutDesignators {

    // 1️⃣ Method-level matching using execution
    // Matches any method in classes under service package
    @Pointcut("execution(* com.atquil.springaop.service.*.*(..))")
    public void serviceMethods() {
        // Use case: Logging, metrics, security checks
    }

    // 2️⃣ Class-level matching using within package
    // Matches all methods declared in classes under service package
    @Pointcut("within(com.atquil.springaop.service..*)")
    public void serviceClassLevel() {
        // Use case: Blanket logging or exception handling
    }

    // 3️⃣ Class-level annotation matching using @within (@AnnotationType *)
    // Matches all methods in classes annotated with @Service
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceAnnotatedClass() {
        // Use case: Apply advice to all service beans
    }

    // 4️⃣ Bean-level matching using target
    // Matches if bean is of a specific class type
    @Pointcut("target(com.atquil.springaop.service.MyPaymentService)")
    public void beanOfType() {
        // Use case: Class-based proxies (CGLIB)
    }

    // 5️⃣ Parameter-level matching using args
    // Matches methods that accept a String parameter
    @Pointcut("args(java.lang.String)")
    public void methodsWithStringArg() {
        // Use case: Input validation, logging sensitive parameters
    }

    // 6️⃣ Annotation-level matching using @annotation
    // Matches methods annotated with @Scheduled
    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledMethods() {
        // Use case: Monitoring scheduled jobs
    }

    // 7️⃣ Proxy-level matching using this
    // Matches if proxy implements the given interface
    @Pointcut("this(com.atquil.springaop.service.MyPaymentServiceInterface)")
    public void proxyImplementsInterface() {
        // Use case: Interface-based proxies (JDK dynamic proxies)
    }

    // 8️⃣ Combined pointcut using logical AND
    // Matches service methods that are also annotated with @Scheduled
    @Pointcut("serviceMethods() && scheduledMethods()")
    public void scheduledServiceMethods() {
        // Use case: Alerting or retry logic for scheduled service tasks
    }

    //  9️⃣ Custom annotation matching
    // Matches methods annotated with @MyCustomValidation
    @Pointcut("@annotation(com.atquil.springaop.config.annotation.MyCustomAnnotationForPaymentValidation)")
    public void customValidationMethods() {
        // Use case: Enforce business rules or input checks
    }
}

