package com.kaiser.financ.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiser.financ.dtos.CredenciaisDTO;
import com.kaiser.financ.services.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JWTUtil jwtUtil;
  private final UsuarioService usuarioService;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UsuarioService usuarioService) {
    setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.usuarioService = usuarioService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException {

    try {
      CredenciaisDTO creds =
          new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);

      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(
              creds.getEmail(), creds.getSenha(), new ArrayList<>());

      Authentication auth = authenticationManager.authenticate(authToken);

      return auth;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
      throws IOException, ServletException {

    UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
    String token = jwtUtil.generateToken(user);
    res.addHeader("Authorization", "Bearer " + token);
    res.addHeader("access-control-expose-headers", "Authorization");

    String body = "{\"email\":\"" + user.getUsername() + "\",\"accessToken\":\"" + token + "\"}";
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    res.getWriter().write(body);

    usuarioService.updateLastLogin(user.getId(), LocalDateTime.now());
  }

  private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
        throws IOException, ServletException {
      response.setStatus(401);
      response.setContentType("application/json");
      response.getWriter().append(json());
    }

    private String json() {
      long date = new Date().getTime();
      return "{\"timestamp\": "
          + date
          + ", "
          + "\"status\": 401, "
          + "\"error\": \"Não autorizado\", "
          + "\"message\": \"Email ou senha inválidos\", "
          + "\"path\": \"/login\"}";
    }
  }
}
