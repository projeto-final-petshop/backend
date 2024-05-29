package br.com.finalproject.petconnect.security.filter;

import br.com.finalproject.petconnect.security.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Para cada solicitação, iremos recuperar o Token JWT no cabeçalho Authorization e valida-ló:
 *
 * <ul>
 *     <li>Se o Token for inválido, rejeite a requisição</li>
 *     <li>Se o Token for válido, extraia o nome de usuário (email), encontre o usuário relacionado no banco de dados
 *     e defina-o no contexto de autenticação para que possa acessá-lo em qualquer camada da aplicação.</li>
 * </ul>
 */
@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtService.extractEmail(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Não foi possível obter o JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("O JWT Token expirou");
            }
        } else {
            logger.warn("JWT Token não começa com Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            log.warn("Requisição sem cabeçalho Authorization ou Bearer inválido");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        try {
//
//            final String jwtToken = authHeader.substring(7);
//            log.info("Token JWT extraído: {}", jwtToken);
//
//            final String userEmail = jwtService.extractUsername(jwtToken);
//            log.info("Usuário extraído do token JWT: {}", userEmail);
//
//            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//                log.info("Detalhes do usuário carregados: {}", userDetails.getUsername());
//
//                if (jwtService.isTokenValid(jwtToken, userDetails)) {
//                    log.info("Token JWT válido para o usuário: {}", userDetails.getUsername());
//
//                    var authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails,
//                            null,
//                            userDetails.getAuthorities()
//                    );
//
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                    log.info("Autenticação bem-sucedida para o usuário: {}", userDetails.getUsername());
//                } else {
//                    log.warn("Token JWT inválido para o usuário: {}", userDetails.getUsername());
//                }
//
//            }
//
//        } catch (Exception exception) {
//            log.error("Erro ao processar a autenticação JWT", exception);
//            handlerExceptionResolver.resolveException(request, response, null, exception);
//            return; // Adiciona um retorno aqui para evitar a continuação do fluxo em caso de exceção
//        }
//        filterChain.doFilter(request, response);
//    }
}
