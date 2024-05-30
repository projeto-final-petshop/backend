package br.com.finalproject.petconnect.security.filter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsrfCookieFilter {

    // extends OncePerRequestFilter

//    @Override
//    protected void doFilterInternal(@Nonnull HttpServletRequest request,
//                                    @Nonnull HttpServletResponse response,
//                                    @Nonnull FilterChain filterChain)
//            throws ServletException, IOException {
//
//        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//        if (csrfToken != null) {
//            log.info("CSRF token obtido: {}", csrfToken.getToken());
//            if (csrfToken.getHeaderName() != null) {
//                log.info("Definindo cabeçalho CSRF: {} com token", csrfToken.getHeaderName());
//                response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
//            }
//        } else {
//            log.warn("CSRF token não encontrado na requisição");
//        }
//        filterChain.doFilter(request, response);
//    }

}