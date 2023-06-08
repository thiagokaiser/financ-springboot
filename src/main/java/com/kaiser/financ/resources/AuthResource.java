package com.kaiser.financ.resources;

import com.kaiser.financ.dto.EmailDTO;
import com.kaiser.financ.dto.ResetPasswordDTO;
import com.kaiser.financ.security.JWTUtil;
import com.kaiser.financ.security.UserSS;
import com.kaiser.financ.services.AuthService;
import com.kaiser.financ.services.impl.UsuarioServiceImpl;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

  @Autowired private JWTUtil jwtUtil;

  @Autowired private AuthService service;

  @PostMapping(value = "/refresh_token")
  public ResponseEntity<String> refreshToken(HttpServletResponse response) {
    UserSS user = UsuarioServiceImpl.authenticated();
    String token = jwtUtil.generateToken(user);
    response.addHeader("Authorization", "Bearer " + token);
    response.addHeader("access-control-expose-headers", "Authorization");
    String body = "{\"email\":\"" + user.getUsername() + "\",\"accessToken\":\"" + token + "\"}";
    return ResponseEntity.ok().body(body);
  }

  @PostMapping(value = "/forgot")
  public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
    service.sendResetPassword(objDto.getEmail());
    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/reset_password/{token}")
  public ResponseEntity<Void> forgot(
      @Valid @RequestBody ResetPasswordDTO objDto, @PathVariable String token) {
    objDto.setToken(token);
    service.changePassword(objDto);
    return ResponseEntity.noContent().build();
  }
}
