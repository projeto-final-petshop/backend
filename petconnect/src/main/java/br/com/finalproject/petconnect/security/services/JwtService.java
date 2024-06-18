package br.com.finalproject.petconnect.security.services;

import br.com.finalproject.petconnect.exceptions.runtimes.generic.DataModificationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
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

    private static final String EXTRACT_EMAIL_ERROR = "Falha ao extrair email do token.";
    private static final String EXTRACT_CLAIM_ERROR = "Falha ao extrair claim do token.";
    private static final String BUILD_TOKEN_ERROR = "Falha na construção do token.";
    private static final String VALIDATE_TOKEN_ERROR = "Falha na validação do token.";
    private static final String EXTRACT_EXPIRATION_ERROR = "Falha ao extrair data de expiração do token.";
    private static final String EXTRACT_ALL_CLAIMS_ERROR = "Falha ao extrair todas as claims do token.";
    private static final String CHECK_TOKEN_EXPIRATION_ERROR = "Erro interno no servidor durante a verificação da expiração do token.";
    private static final String GENERATE_TOKEN_ERROR = "Erro interno no servidor durante a geração do token.";
    private static final String GENERATE_TOKEN_WITH_CLAIMS_ERROR = "Falha ao gerar token com claims extras.";

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String extractEmail(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (JwtException | IllegalArgumentException e) {
            log.error(EXTRACT_EMAIL_ERROR, e);
            throw new DataModificationException(EXTRACT_EMAIL_ERROR, e);
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (JwtException | IllegalArgumentException e) {
            log.error(EXTRACT_CLAIM_ERROR, e);
            throw new DataModificationException(EXTRACT_CLAIM_ERROR, e);
        }
    }

    public String generateToken(UserDetails userDetails) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("name", userDetails.getUsername());
            claims.put("email", userDetails.getUsername());
            claims.put("roles", userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).toList());
            return generateToken(claims, userDetails);
        } catch (Exception e) {
            log.error(GENERATE_TOKEN_ERROR, e);
            throw new DataModificationException(GENERATE_TOKEN_ERROR, e);
        }
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        try {
            return buildToken(extraClaims, userDetails, jwtExpiration);
        } catch (Exception e) {
            log.error(GENERATE_TOKEN_WITH_CLAIMS_ERROR, e);
            throw new DataModificationException(GENERATE_TOKEN_WITH_CLAIMS_ERROR, e);
        }
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        try {
            return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            log.error(BUILD_TOKEN_ERROR, e);
            throw new DataModificationException(BUILD_TOKEN_ERROR, e);
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractEmail(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (Exception e) {
            log.error(VALIDATE_TOKEN_ERROR, e);
            throw new DataModificationException(VALIDATE_TOKEN_ERROR, e);
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            log.error(CHECK_TOKEN_EXPIRATION_ERROR, e);
            throw new DataModificationException(CHECK_TOKEN_EXPIRATION_ERROR, e);
        }
    }

    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            log.error(EXTRACT_EXPIRATION_ERROR, e);
            throw new DataModificationException(EXTRACT_EXPIRATION_ERROR, e);
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            log.error(EXTRACT_ALL_CLAIMS_ERROR, e);
            throw new DataModificationException(EXTRACT_ALL_CLAIMS_ERROR, e);
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}