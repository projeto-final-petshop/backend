package br.com.finalproject.petconnect.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /**
     * Title: um breve resumo do tipo de problema. Não deve mudar para ocorrências do mesmo tipo, exceto para fins de
     * localização;
     */
    private String title;

    /**
     * Status: o status HTTP gerado pelo servidor de origem. Normalmente deve ser o mesmo status HTTP da resposta, e
     * pode servir de referência para casos onde um servidor proxy altera o status da resposta;
     */
    private int status;

    /**
     * Detail: descrição detalhada do problema;
     */
    private String detail;

    /**
     * Instance: propriedade opcional, com um URI exclusivo para o erro específico, que geralmente aponta para um log de
     * erros para essa resposta.
     */
    private String instance;

    /**
     * Type: uma URL para um documento que descreva o tipo do problema;
     */
    private String type;

    private Map<String, String> properties = new HashMap<>();

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

}
