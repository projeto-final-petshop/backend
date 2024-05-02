package br.com.projetofinal.petconnet.core.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro para configuração do cookie CSRF (Cross-Site Request Forgery).
 * <p>
 * Este filtro é executado apenas uma vez por requisição e adiciona o token CSRF como um cookie na resposta HTTP. O
 * token CSRF é recuperado do atributo da requisição e configurado no cabeçalho (header) especificado pelo token.
 * <p>
 * O filtro assume que o atributo da requisição contendo o token CSRF tenha o nome completo da classe {@link CsrfToken}
 *
 * @author juliane.maran
 * @see CsrfToken
 * @see OncePerRequestFilter
 * @see FilterChain
 */
public class CsrfCookieFilter extends OncePerRequestFilter {

    /**
     * Realiza a interceptação da requisição (apenas uma vez por requisição).
     *
     * @param request
     *         Objeto que representa a requisição HTTP.
     * @param response
     *         Objeto que representa a resposta HTTP.
     * @param filterChain
     *         Cadeia de filtros do qual o filtro atual faz parte.
     *
     * @throws ServletException
     *         Exceção caso ocorra um erro relacionado ao Servlet.
     * @throws IOException
     *         Exceção caso ocorra um erro de entrada/saída.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Recupera o token CSRF do atributo da requisição
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        // Verifica se o token CSRF possui um nome de cabeçalho válido
        if (null != csrfToken.getHeaderName()) {
            // Adiciona o token CSRF como um cookie na resposta
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }

        // Prossegue na cadeia de filtros
        filterChain.doFilter(request, response);
    }

}