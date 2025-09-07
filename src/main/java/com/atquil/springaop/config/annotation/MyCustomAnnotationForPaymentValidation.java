package com.atquil.springaop.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author atquil
 */

// 📌 Custom Annotation can be used in validation logic or AOP interceptors
@Retention(RetentionPolicy.RUNTIME)  // Makes annotation available at runtime for reflection/AOP
@Target(ElementType.METHOD)          // Can only be applied to methods
public @interface MyCustomAnnotationForPaymentValidation {
    // 🧭 Marker annotation — no fields required
//    String value() default "";
//    boolean logParameters() default true;
//    boolean logResult() default false;
}
