package br.com.finalproject.petconnect.security.filter;

import br.com.finalproject.petconnect.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // Ignora a rota de cadastro
        String requestURI = request.getRequestURI();
        if ("/auth/signup" .equals(requestURI)) {
            log.info("Rota de cadastro acessada, ignorando filtro JWT: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        try {

            final String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Requisição sem cabeçalho Authorization ou Bearer inválido");
                filterChain.doFilter(request, response);
                return;
            }

            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractEmail(jwt);
            log.info("Token JWT extraído e usuário identificado: {}", userEmail);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                log.info("Detalhes do usuário carregados: {}", userDetails.getUsername());

                if (jwtService.isTokenValid(jwt, userDetails)) {

                    log.info("Token JWT válido para o usuário: {}", userDetails.getUsername());

                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("Autenticação bem-sucedida para o usuário: {}", userDetails.getUsername());
                }
                log.warn("Token JWT inválido para o usuário: {}", userDetails.getUsername());
            }

            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            log.error("Erro ao processar a autenticação JWT", exception);
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}