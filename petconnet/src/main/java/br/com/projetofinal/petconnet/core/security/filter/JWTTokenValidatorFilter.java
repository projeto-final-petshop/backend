package br.com.projetofinal.petconnet.core.security.filter;

import br.com.projetofinal.petconnet.core.security.SecurityConstants;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Filtro para validação de token JWT (JSON Web Token).
 * <p>
 * Este filtro intercepta a requisição e valida o token JWT presente no cabeçalho especificado por
 * {@code SecurityConstants.JWT_HEADER}. Caso o token seja válido, ele extrai as informações de usuário (username) e
 * autoridades (authorities) do payload do token e configura a autenticação no contexto de segurança usando
 * {@link SecurityContextHolder}
 * <p>
 * O filtro também permite a configuração de rotas que não devem ser filtradas através do método
 * {@code shouldNotFilter}. Por padrão, a rota /auth/login não é filtrada, assumindo que seja o endpoint de login da
 * aplicação.
 */
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

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

        // Recupera o token JWT do cabeçalho da requisição
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);

        // Verifica se o token JWT está presente
        if (null != jwt) {

            try {
                Claims claims = parseJwt(jwt);
                String username = getUsernameFromClaims(claims);
                List<GrantedAuthority> authorities = getAuthoritiesFromClaims(claims);
                // Cria a autenticação com base nas informações extraídas
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                // Configura a autenticação no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // Lança exceção caso a validação do token falhe
                throw new BadCredentialsException("Token inválido recebido!");
            }

        }
        // Prossegue na cadeia de filtros
        filterChain.doFilter(request, response);

    }

    private Claims parseJwt(String jwt) {

        // Configura a chave secreta para validação do token
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

        // Realiza a validação do token e extrai o payload
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

    }

    private String getUsernameFromClaims(Claims claims) {
        // Extrai as informações de usuário e autoridades do payload
        return String.valueOf(claims.get("username"));
    }

    private List<GrantedAuthority> getAuthoritiesFromClaims(Claims claims) {
        String authorities = (String) claims.get("authorities");
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    }

    /**
     * Verifica se a requisição deve ser filtrada.
     * <p>
     * Por padrão, este método retorna true, indicando que a requisição deve ser filtrada. No entanto, ele permite a
     * configuração de rotas que não devem ser filtradas.
     *
     * @param request
     *         Objeto que representa a requisição HTTP.
     *
     * @return true se a requisição deve ser filtrada, false caso contrário.
     *
     * @throws ServletException
     *         Exceção caso ocorra um erro relacionado ao Servlet.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Por padrão, não filtra a rota de login return
        return request.getServletPath().equals("/auth/login");
    }

}
