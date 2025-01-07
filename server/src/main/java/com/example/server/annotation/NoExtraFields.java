package com.example.server.annotation;

import com.example.server.component.NoExtraFieldsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NoExtraFieldsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoExtraFields {
    String message() default "Unexpected fields found in JSON";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

