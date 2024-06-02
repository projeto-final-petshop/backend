package br.com.finalproject.petconnect.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    private static final long ALLOWED_CLOCK_SKEW_MILLIS = 300000; // 5 minutos

    public String extractEmail(String token) {
        log.info("Extracting username (subject) from JWT token: {}", token);
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.info("Extraindo claim do token JWT");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        log.info("Gerando token JWT para o usuário: {}", userDetails.getUsername());
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        log.info("Gerando token JWT com claims adicionais para o usuário: {}", userDetails.getUsername());
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        log.info("Construindo token JWT para o usuário: {}", userDetails.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.info("Validando token JWT para o usuário: {}", userDetails.getUsername());
        final String username = extractEmail(token);
        boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        if (isValid) {
            log.info("Token JWT válido para o usuário: {}", userDetails.getUsername());
        } else {
            log.warn("Token JWT inválido para o usuário: {}", userDetails.getUsername());
        }
        return isValid;
    }

//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }

    private boolean isTokenExpired(String token) {
        log.info("Verificando se o token JWT está expirado");
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        log.info("Extraindo data de expiração do token JWT");
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        log.info("Extraindo todos os claims do token JWT");
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                // permite um pequeno desvio no tempo (clock skew) ao validar tokens JWT para evitar problemas
                // de sicronização de tempo entre o cliente e o servidor.
                //.setAllowedClockSkewSeconds(ALLOWED_CLOCK_SKEW_MILLIS / 1000)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        log.info("Gerando chave de assinatura para o token JWT");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}