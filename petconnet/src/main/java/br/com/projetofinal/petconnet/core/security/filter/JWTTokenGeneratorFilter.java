package br.com.projetofinal.petconnet.core.security.filter;

import br.com.projetofinal.petconnet.core.security.SecurityConstants;
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
 * Esta classe é um filtro de servlet que estende {@link OncePerRequestFilter}. Ela é responsável por gerar e adicionar
 * um token JWT ao cabeçalho da resposta HTTP se uma autenticação válida for encontrada. O filtro é aplicado apenas a
 * solicitações que não sejam para o caminho "/auth/login".
 *
 * @author juliane.maran
 */
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    /**
     * Este método é chamado uma vez por solicitação HTTP.
     * <p>
     * Recupera o objeto de autenticação do contexto de segurança.
     * <p>
     * Se a autenticação for válida:
     *
     * <ul>
     *     <li>Gera um token JWT contendo o nome de usuário e as autoridades do usuário autenticado.</li>
     *     <li>Define o cabeçalho JWT_HEADER da resposta com o token JWT gerado.</li>
     * </ul>
     * <p>
     * Chama o próximo filtro na cadeia de filtros.
     *
     * @param request
     *         O objeto de solicitação HTTP atual.
     * @param response
     *         O objeto de resposta HTTP atual.
     * @param filterChain
     *         A cadeia de filtros do servlet.
     *
     * @throws ServletException
     *         Exceção caso ocorra um erro relacionado ao Servlet.
     * @throws IOException
     *         Exceção caso ocorra um erro de entrada/saída.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Recupera a autenticação do contexto de segurança (SecurityContextHolder).
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null != authentication) {

            // Cria uma chave secreta a partir da constante SecurityConstants.JWT_KEY.
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY
                    .getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder()
                    .issuer("Pet Connect") // Definindo o emissor como "Pet Connect".
                    .subject("JWT Token") // Definindo o assunto como "JWT Token".
                    // Incluindo a reivindicação "username" com o nome do usuário autenticado.
                    .claim("username", authentication.getName())
                    // Incluindo a reivindicação "authorities" com as autoridades do usuário (convertidas para String).
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .issuedAt(new Date()) // Definindo a data de criação do token.
                    // Definindo a data de expiração do token (30 minutos a partir da criação).
//                    .expiration(new Date((new Date()).getTime() + 30000000))
                    .expiration(new Date((new Date()).getTime() + (1000 * 60 * 60 * 24))) // expira em 24h
                    .signWith(key).compact(); // Assinando o token com a chave secreta.

            // Adiciona o token JWT no cabeçalho da resposta (response) usando a chave SecurityConstants.JWT_HEADER.
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);

        }

        // Chama o próximo filtro da cadeia (filterChain.doFilter).
        filterChain.doFilter(request, response);

    }

    /**
     * Este método privado converte uma coleção de autoridades em uma string separada por vírgulas.
     * <p>
     * Percorre a coleção de autoridades e adiciona cada autoridade a um conjunto.
     * <p>
     * Une os elementos do conjunto com vírgulas e retorna a string resultante.
     *
     * @param request
     *         O objeto de solicitação HTTP atual.
     *
     * @return Retorna true se o filtro não deve ser aplicado, false caso contrário.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/auth/login");
    }

    /**
     * Converte uma coleção de autoridades ({@link GrantedAuthority}) para uma String separada por vírgulas.
     * <p>
     * Utilitário para extrair as autoridades do usuário e incluí-las no token JWT.
     *
     * @param collection
     *         Uma coleção de objetos {@link GrantedAuthority} representando as autoridades do usuário.
     *
     * @return Retorna uma string separada por vírgulas contendo as autoridades do usuário.
     */
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

}
