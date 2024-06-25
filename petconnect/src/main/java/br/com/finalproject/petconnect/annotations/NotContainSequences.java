package br.com.finalproject.petconnect.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotContainSequencesValidator.class)
@Documented
public @interface NotContainSequences {

    String message() default "A senha não pode conter sequências simples.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}



