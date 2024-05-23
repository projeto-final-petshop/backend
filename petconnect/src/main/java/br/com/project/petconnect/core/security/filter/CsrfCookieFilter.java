package br.com.project.petconnect.core.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de servlet para configuração do cookie CSRF (Cross-Site Request Forgery).
 * <p>
 * Este filtro estende {@link OncePerRequestFilter} do Spring Security e é executado apenas uma vez por requisição. Sua
 * função é recuperar o token CSRF do atributo da requisição e, se o token possuir um nome de cabeçalho HTTP, ele
 * adiciona o token como um cabeçalho na resposta HTTP.
 * <p>
 * O token CSRF é utilizado para prevenir ataques de falsificação de requisição entre sites.
 *
 * @author juliane.maran
 */
public class CsrfCookieFilter extends OncePerRequestFilter {

    /**
     * Sobrescreve o método do filtro para realizar a lógica de adição do token CSRF no cabeçalho da resposta.
     *
     * @param request
     *         Objeto da requisição HTTP.
     * @param response
     *         Objeto da resposta HTTP.
     * @param filterChain
     *         Cadeia de filtros do Spring Security.
     *
     * @throws ServletException
     *         Exceção caso haja erro no filtro do servlet.
     * @throws IOException
     *         Exceção caso haja erro de entrada/saída.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Recupera o token CSRF do atributo da requisição.
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        // Verifica se o token possui um nome de cabeçalho HTTP configurado.
        if (null != csrfToken.getHeaderName()) {
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }

        // Prossegue na cadeia de filtros do Spring Security.
        filterChain.doFilter(request, response);
    }

}
