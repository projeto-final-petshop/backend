package br.com.projetofinal.petconnet.core.security.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Filtro de registro de início da validação de autenticação.
 * <p>
 * Este filtro intercepta a requisição no início da cadeia de filtros e registra no log uma mensagem informando que a
 * validação de autenticação está em andamento.
 * <p>
 * O filtro utiliza a biblioteca SLF4J para realizar o log.
 *
 * @author juliane.maran
 */
@Slf4j
public class AuthoritiesLoggingAtFilter implements Filter {

    /**
     * Realiza a interceptação da requisição.
     *
     * @param request
     *         Objeto que representa a requisição HTTP.
     * @param response
     *         Objeto que representa a resposta HTTP.
     * @param chain
     *         Cadeia de filtros do qual o filtro atual faz parte.
     *
     * @throws IOException
     *         Exceção caso ocorra um erro de entrada/saída.
     * @throws ServletException
     *         Exceção caso ocorra um erro relacionado ao Servlet.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Registra mensagem informando o início da validação de autenticação
        log.info("Authentication Validation is in progress");

        // Prossegue na cadeia de filtros, permitindo que a requisição avance para
        // o próximo filtro.
        chain.doFilter(request, response);

    }

}
