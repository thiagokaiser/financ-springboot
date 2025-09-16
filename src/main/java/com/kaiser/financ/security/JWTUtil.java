package com.kaiser.financ.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

  private final Key key;

  @Value("${jwt.expiration}")
  private Long expiration;

  public JWTUtil(@Value("${jwt.secret}") String secret) {
    // Garante chave segura para HS512
    if (secret.getBytes(StandardCharsets.UTF_8).length < 64) {
      this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    } else {
      this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
  }

  public String generateToken(UserDetailsImpl user) {
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(key, SignatureAlgorithm.HS512)
        .claim("id", user.getId())
        .claim("email", user.getUsername())
        .claim("nome", user.getNome())
        .claim("sobrenome", user.getSobrenome())
        .compact();
  }

  public String generateTokenResetPassword(String email) {
    return Jwts.builder()
        .setSubject(email)
        .setExpiration(new Date(System.currentTimeMillis() + 600_000))
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();
  }

  public boolean tokenValido(String token) {
    Claims claims = getClaims(token);
    if (claims != null) {
      String username = claims.getSubject();
      Date expirationDate = claims.getExpiration();
      return username != null && expirationDate != null && new Date().before(expirationDate);
    }
    return false;
  }

  public String getUsername(String token) {
    Claims claims = getClaims(token);
    if (claims != null) {
      return claims.getSubject();
    }
    return null;
  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (Exception e) {
      return null;
    }
  }
}
