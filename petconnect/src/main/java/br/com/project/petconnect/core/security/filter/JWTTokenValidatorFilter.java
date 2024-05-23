package br.com.project.petconnect.core.security.filter;

import br.com.project.petconnect.core.security.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Essa classe é um filtro que valida o Token JWT presente no cabeçalho da requisição. Ela verifica a autenticidade do
 * Token e, se válido, autentica o usuário no contexto de segurança do Spring.
 * <p>
 * Filtro que valida o token JWT presente no cabeçalho da requisição.
 * <p>
 * Verifica a autenticidade do token e, se válido, autentica o usuário no contexto de segurança.
 *
 * @author juliane.maran
 */
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    /**
     * Método que realiza a filtragem interna da requisição, verificando e validando o token JWT.
     *
     * @param request
     *         a requisição HTTP
     * @param response
     *         a resposta HTTP
     * @param filterChain
     *         a cadeia de filtros
     *
     * @throws ServletException
     *         em caso de erro de servlet
     * @throws IOException
     *         em caso de erro de I/O
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtém o token JWT do cabeçalho da requisição.
        String jwt = request.getHeader(SecurityConstants.AUTHORIZATION);
        // Verifica se o token não é nulo.
        if (null != jwt) {
            try {
                // Usa a chave secreta para validar e decodificar o token.
                SecretKey key = Keys.hmacShaKeyFor(
                        SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
                // Extrai o nome de usuário e as autoridades do token.
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");

                // Cria um objeto Authentication e define no contexto de segurança do Spring.
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // Em caso de falha na validação, lança uma exceção de credenciais inválidas.
                throw new BadCredentialsException("Token inválido recebido!");
            }

        }

        filterChain.doFilter(request, response);
    }

    /**
     * Método que verifica se a filtragem deve ser realizada para a requisição atual.
     * <p>
     * Este filtro não é aplicado à rota de login (/auth/login).
     *
     * @param request
     *         a requisição HTTP
     *
     * @return true se a requisição não deve ser filtrada, false caso contrário
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Define que o filtro não deve ser aplicado na rota de login (/auth/login).
        return request.getServletPath().equals("/auth/login");
    }

}
