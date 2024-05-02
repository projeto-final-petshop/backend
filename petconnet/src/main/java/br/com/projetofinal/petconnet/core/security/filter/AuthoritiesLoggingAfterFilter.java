package br.com.projetofinal.petconnet.core.security.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

/**
 * Filtro de registro de autoridades do usuário após a requisição.
 * <p>
 * Esse filtro intercepta a requisição após a cadeia de filtros e registra no log informações sobre a autenticação do
 * usuário.
 * <p>
 * O filtro utiliza a biblioteca SLF4J para realizar o log.
 *
 * @author juliane.maran
 */
@Slf4j
public class AuthoritiesLoggingAfterFilter implements Filter {

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

        // Recupera o contexto de autenticação do SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se a autenticação está presente
        if (null != authentication) {
            // Registra no log informações sobre o usuário autenticado
            log.info("User {} is successfully authenticated and has the authorities: {}",
                    authentication.getName(), authentication.getAuthorities());
        }

        // Prossegue na cadeia de filtros
        chain.doFilter(request, response);

    }

}
