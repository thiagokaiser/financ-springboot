package com.kaiser.financ.services.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsuarioInsertValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsuarioInsert {
  String message() default "Erro de validação";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
