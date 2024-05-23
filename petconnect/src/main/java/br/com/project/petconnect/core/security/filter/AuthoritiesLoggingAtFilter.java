package br.com.project.petconnect.core.security.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Essa classe é um filtro que faz o log indicando que a validação da autenticação está em andamento.
 * <p>
 * Filtro que faz o log indicando que a validação da autenticação está em andamento.
 *
 * @author juliane.maran
 */
@Slf4j
public class AuthoritiesLoggingAtFilter implements Filter {

    /**
     * Método de inicialização do filtro.
     * <p>
     * Este método é chamado pelo contêiner da web quando o filtro é inicializado. Pode ser usado para configurar
     * recursos necessários para o filtro.
     *
     * @param filterConfig
     *         configuração do filtro
     *
     * @throws ServletException
     *         em caso de erro na inicialização do filtro
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialização de recursos necessários para o filtro, se houver.
        log.info("AuthoritiesLoggingAtFilter inicializado.");
    }

    /**
     * Método que realiza a filtragem da requisição, fazendo o log indicando que a validação da autenticação está em
     * andamento.
     *
     * @param request
     *         a requisição HTTP
     * @param response
     *         a resposta HTTP
     * @param filterChain
     *         a cadeia de filtros
     *
     * @throws IOException
     *         em caso de erro de I/O
     * @throws ServletException
     *         em caso de erro de servlet
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("Authentication Validation is in progress");
        filterChain.doFilter(request, response);
    }

    /**
     * Método de destruição do filtro.
     * <p>
     * Este método é chamado pelo contêiner da web quando o filtro está sendo destruído. Pode ser usado para liberar
     * recursos alocados durante a execução do filtro.
     */
    @Override
    public void destroy() {
        // Liberação de recursos alocados pelo filtro, se houver.
        log.info("AuthoritiesLoggingAtFilter destruído.");
    }

}
