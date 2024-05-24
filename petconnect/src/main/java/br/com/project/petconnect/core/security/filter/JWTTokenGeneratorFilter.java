package br.com.project.petconnect.core.security.filter;

import br.com.project.petconnect.core.security.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Essa classe é um filtro que gera o Token JWT após a autenticação bem-sucedida do usuário. O Token é adicionado ao
 * cabeçalho da resposta HTTP.
 * <p>
 * Filtro que gera um token JWT após a autenticação bem-sucedida do usuário.
 * <p>
 * O token é adicionado ao cabeçalho da resposta HTTP.
 *
 * @author juliane.maran
 */
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    /**
     * Método que realiza a filtragem interna da requisição, gerando um token JWT após a autenticação.
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

        // Obtém o objeto Authentication do contexto de segurança do Spring.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Verifica se a autenticação não é nula.
        if (null != authentication) {
            // Usa a chave secreta para criar um token JWT com informações do usuário e autoridades.
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder().issuer("PetConnect").subject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(key).compact();
            // Define o token JWT no cabeçalho da resposta.
            response.setHeader(SecurityConstants.AUTHORIZATION, jwt);

        }

        filterChain.doFilter(request, response);
    }

    /**
     * Método que verifica se a filtragem deve ser realizada para a requisição atual.
     * <p>
     * Este filtro é aplicado apenas à rota de login (/auth/login).
     *
     * @param request
     *         a requisição HTTP
     *
     * @return true se a requisição não deve ser filtrada, false caso contrário
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Define que o filtro só deve ser aplicado na rota de login (/auth/login).
        return !request.getServletPath().equals("/auth/login");
    }

    /**
     * Método auxiliar que converte a coleção de autoridades em uma string separada por vírgulas.
     *
     * @param authorities
     *         a coleção de autoridades
     *
     * @return uma string com as autoridades separadas por vírgulas
     */
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        // Converte a coleção de autoridades em uma string separada por vírgulas para incluir no token JWT.
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

}
