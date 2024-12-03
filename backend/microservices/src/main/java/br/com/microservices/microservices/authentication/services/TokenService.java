package br.com.microservices.microservices.authentication.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

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
    private String secret;

    private SecretKey secretKey;

    public TokenService() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generatedToken(Usuario user) {
        try {
            String token = Jwts.builder()
                    .setIssuer("apiecomerce")
                    .setSubject(user.getUsername())
                    .setExpiration(genExpirationDate())
                    .claim("role", user.getRole().getRole())
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
                    .requireIssuer("apiecomerce")
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
                    .setSigningKey(secretKey) // Define a chave para validação
                    .build()
                    .parseClaimsJws(token) // Decodifica o token
                    .getBody(); // Obtém o corpo (claims) do token

            return claims.getSubject(); // Retorna o usuário (subject) do token
        } catch (ExpiredJwtException e) {
            // Token expirado
            throw new RuntimeException("Token expirado: " + e.getMessage(), e);
        } catch (SignatureException e) {
            // Assinatura inválida
            throw new RuntimeException("Assinatura inválida: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            // Token malformado
            throw new RuntimeException("Token malformado: " + e.getMessage(), e);
        } catch (Exception e) {
            // Outros erros
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
