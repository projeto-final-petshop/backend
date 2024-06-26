package br.com.finalproject.petconnect.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CepValidator implements ConstraintValidator<ValidCep, String> {

    private static final String CEP_PATTERN = "\\d{5}-\\d{3}";

    @Override
    public void initialize(ValidCep constraintAnnotation) {
        // Inicializador, se necessário
    }

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if (cep == null) {
            return false;
        }
        return cep.matches(CEP_PATTERN);
    }

}