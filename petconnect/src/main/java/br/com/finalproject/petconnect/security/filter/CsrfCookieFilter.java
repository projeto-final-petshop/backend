package br.com.finalproject.petconnect.security.filter;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class CsrfCookieFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            log.info("[ CsrfCookieFilter ] CSRF token obtido: {}", csrfToken.getToken());
            if (csrfToken.getHeaderName() != null) {
                log.info("[ CsrfCookieFilter ] Definindo cabeçalho CSRF: {} com token", csrfToken.getHeaderName());
                response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
            }
            var csrfCookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
            csrfCookie.setPath("/");
            csrfCookie.setHttpOnly(false);
            csrfCookie.setSecure(request.isSecure());
            response.addCookie(csrfCookie);
        } else {
            log.warn("Warn: CSRF token não encontrado na requisição");
        }

        filterChain.doFilter(request, response);
    }

}