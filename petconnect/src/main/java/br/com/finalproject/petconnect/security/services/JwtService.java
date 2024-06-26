package br.com.finalproject.petconnect.security.services;

import br.com.finalproject.petconnect.exceptions.runtimes.service.JWTServiceException;
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

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String extractEmail(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Erro ao extrair e-mail do token JWT: {}", e.getMessage());
            throw new JWTServiceException("Erro ao extrair e-mail do token JWT", e);
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Erro ao extrair claim do token JWT: {}", e.getMessage());
            throw new JWTServiceException("Erro ao extrair claim do token JWT", e);
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
            log.error("Erro ao gerar token JWT: {}", e.getMessage());
            throw new JWTServiceException("Erro ao gerar token JWT", e);
        }
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        try {
            return buildToken(extraClaims, userDetails, jwtExpiration);
        } catch (Exception e) {
            log.error("Erro ao gerar token JWT com claims: {}", e.getMessage());
            throw new JWTServiceException("Erro ao gerar token JWT com claims", e);
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
            log.error("Erro ao construir token JWT: {}", e.getMessage());
            throw new JWTServiceException("Erro ao construir token JWT", e);
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractEmail(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Erro ao validar token JWT: {}", e.getMessage());
            throw new JWTServiceException("Erro ao validar token JWT", e);
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            log.error("Erro ao verificar expiração do token JWT: {}", e.getMessage());
            throw new JWTServiceException("Erro ao verificar expiração do token JWT", e);
        }
    }

    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            log.error("Erro ao extrair expiração do token JWT: {}", e.getMessage());
            throw new JWTServiceException("Erro ao extrair expiração do token JWT", e);
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
            log.error("Erro ao extrair todos os claims do token JWT: {}", e.getMessage());
            throw new JWTServiceException("Erro ao extrair todos os claims do token JWT", e);
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}