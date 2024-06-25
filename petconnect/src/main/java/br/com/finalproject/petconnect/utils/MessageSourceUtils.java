package br.com.finalproject.petconnect.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageSourceUtils {

    private final MessageSource messageSource;

    // Método para buscar mensagem do arquivo de propriedades
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    // Método para buscar mensagem do arquivo de propriedades em uma determinada localidade
    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    // Método para buscar mensagem do arquivo de propriedades com argumentos
    public String getMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    // Método para buscar mensagem do arquivo de propriedades com argumentos em uma determinada localidade
    public String getMessage(String key, Object[] args, Locale locale) {
        return messageSource.getMessage(key, args, locale);
    }

}
