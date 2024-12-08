package br.com.microservices.microservices.authentication.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.microservices.microservices.authentication.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secretApi;

    private SecretKey secretKey;

    public TokenService() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generatedToken(Usuario user) {
        try {
            String roles = user.getRole().stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
            String token = Jwts.builder()
                    .setIssuer(secretApi)
                    .setSubject(user.getUsername())
                    .setExpiration(genExpirationDate())
                    .claim("role", roles)
                    .signWith(secretKey)
                    .compact();
            return token;
        } catch (JwtException e) {
            throw new RuntimeException("Error while generation token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .requireIssuer(secretApi)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (JwtException exception) {
            return "";
        }
    }

    private Date genExpirationDate() {
        Instant expiration = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        return Date.from(expiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public String extractUserRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    // Extrair o username (subject) do token
    public String extrairUsuario(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado: " + e.getMessage(), e);
        } catch (SignatureException e) {
            throw new RuntimeException("Assinatura inválida: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Token malformado: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair usuário do token: " + e.getMessage(), e);
        }
    }

    // public boolean isTokenExpired(String token) {
    // return extractAllClaims(token).getExpiration().before(new Date());
    // }

    public boolean validateTokenFromUsername(String token, String username) {
        boolean validacao = false;
        try {
            String usernameToken = extrairUsuario(token);
            if (username.equals(usernameToken)) {
                return validacao = true;
            }
            System.out.println(validacao);
            return false;
        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException e) {
            // Exceções comuns de um token inválido
            return validacao;
        }
    }

}
