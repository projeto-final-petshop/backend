<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
package br.com.finalproject.petconnect.security.services;
========
package br.com.finalproject.petconnect.services;
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java

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

    public String extractUsername(String token) {
<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
        log.info("Extraindo username do token JWT");
========
        log.info("Extrair nome de usuário");
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
        log.info("Extraindo claim do token JWT");
========
        log.info("Extrair informação");
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
        log.info("Gerando token JWT para o usuário: {}", userDetails.getUsername());
========
        log.info("Gerar token");
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
        log.info("Gerando token JWT com claims adicionais para o usuário: {}", userDetails.getUsername());
========
        log.info("Gerar informações do token");
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        log.info("Obter tempo de expiração");
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
        log.info("Construindo token JWT para o usuário: {}", userDetails.getUsername());
========
        log.info("construir token");
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java
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
<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
        log.info("Validando token JWT para o usuário: {}", userDetails.getUsername());
========
        log.info("Token é valido?");
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java
        final String username = extractUsername(token);
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
<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
        log.info("Verificando se o token JWT está expirado");
========
        log.info("Token está expirado?");
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
        log.info("Extraindo data de expiração do token JWT");
========
        log.info("Extrair data de expiração do token");
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
        log.info("Extraindo todos os claims do token JWT");
========
        log.info("Extrair todas as informações");
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
<<<<<<<< HEAD:petconnect/src/main/java/br/com/finalproject/petconnect/security/services/JwtService.java
        log.info("Gerando chave de assinatura para o token JWT");
========
        log.info("Obter chave de login");
>>>>>>>> 1c434ed80b57f6c4415caa2e50e19d38035df597:petconnect/src/main/java/br/com/finalproject/petconnect/services/JwtService.java
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
