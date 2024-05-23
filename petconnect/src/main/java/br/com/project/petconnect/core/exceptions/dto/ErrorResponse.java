package br.com.project.petconnect.core.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe utilizada para representar uma resposta de erro na API.
 * <p>
 * &#64;Builder Anotação do Lombok para gerar construtor com builder pattern
 * <p>
 * &#64;Data Anotação do Lombok para gerar getters, setters, equals, hashCode, e toString
 * <p>
 * &#64;AllArgsConstructor Anotação do Lombok para gerar construtor com todos os argumentos
 * <p>
 * &#64;NoArgsConstructor Anotação do Lombok para gerar construtor sem argumentos
 *
 * @author juliane.maran
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /**
     * Código HTTP do erro (ex.: 400, 404, 500)
     */
    private Integer status;
    /**
     * Código identificador do erro (deve corresponder ao nome de um enum {@link ErrorStatus})
     */
    private String error;
    /**
     * Mensagem descritiva do erro
     */
    private String message;

    /**
     * Construtor alternativo que recebe um objeto {@link ErrorStatus} e popula os campos da classe.
     *
     * @param errorStatus
     *         O objeto contendo o código e mensagem do erro
     */
    public ErrorResponse(ErrorStatus errorStatus) {
        this.status = errorStatus.getHttpStatusCode();
        this.message = errorStatus.getMessage();
        this.error = errorStatus.name();
    }

}
