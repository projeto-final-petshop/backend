package br.com.project.petconnect.security.filter;

import br.com.project.petconnect.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
 * Filtro de autenticação JWT. Este filtro intercepta as requisições e verifica se o cabeçalho de autorização contém um
 * token JWT válido. Se o token for válido, o filtro carrega os detalhes do usuário a partir do serviço de userDetails e
 * define a autenticação no contexto de segurança.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Resolvedor de exceções para tratar exceções durante a autenticação.
     */
    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Serviço para processamento de tokens JWT.
     */
    private final JwtService jwtService;

    /**
     * Serviço para carregar detalhes do usuário.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Construtor do filtro.
     *
     * @param handlerExceptionResolver
     *         Resolvedor de exceções para tratar exceções durante a autenticação.
     * @param jwtService
     *         Serviço para processamento de tokens JWT.
     * @param userDetailsService
     *         Serviço para carregar detalhes do usuário.
     */
    public JwtAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver,
                                   JwtService jwtService,
                                   UserDetailsService userDetailsService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Realiza a filtragem da requisição.
     *
     * @param request
     *         Objeto da requisição HTTP.
     * @param response
     *         Objeto da resposta HTTP.
     * @param filterChain
     *         Cadeia de filtros de segurança.
     *
     * @throws ServletException
     *         Exceção caso ocorra algum erro no filtro.
     * @throws IOException
     *         Exceção caso ocorra algum erro de entrada/saída.
     */
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

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.error("Erro durante a autenticação JWT", exception);
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

}
