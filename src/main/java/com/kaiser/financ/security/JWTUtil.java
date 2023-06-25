package com.kaiser.financ.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String generateToken(UserDetailsImpl user) {

    return Jwts.builder()
        .setSubject(user.getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS512, secret.getBytes())
        .claim("id", user.getId())
        .claim("email", user.getUsername())
        .claim("nome", user.getNome())
        .claim("sobrenome", user.getSobrenome())
        .compact();
  }

  public String generateTokenResetPassword(String email) {

    return Jwts.builder()
        .setSubject(email)
        .setExpiration(new Date(System.currentTimeMillis() + 600000))
        .signWith(SignatureAlgorithm.HS512, secret.getBytes())
        .compact();
  }

  public boolean tokenValido(String token) {
    Claims claims = getClaims(token);
    if (claims != null) {
      String username = claims.getSubject();
      Date expirationDate = claims.getExpiration();
      Date now = new Date(System.currentTimeMillis());
      return username != null && expirationDate != null && now.before(expirationDate);
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
      return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      return null;
    }
  }
}
