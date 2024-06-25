package br.com.finalproject.petconnect.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotContainSequencesValidator implements ConstraintValidator<NotContainSequences, String> {

    @Override
    public void initialize(NotContainSequences constraintAnnotation) {
        // inicialização
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // Se o valor da senha for 'null' retorna true
        if (value == null) {
            return true; // null values are validated by @NotBlank or similar annotation
        }

        // Verificar se a senha contém sequências simples como "abc", "ABC", "123"
        // Caso encontre alguma dessas sequências, a validação irá retornar 'false', indicando que a senha cotém sequências simples
        return !value.toLowerCase().contains("abc") &&
                !value.toUpperCase().contains("ABC") &&
                !value.contains("123"); // A senha contém sequências simples

        // Caso não encontre nenhuma das sequências simples, retorna 'true' indicando que a senha passou na validação.

    }
}
