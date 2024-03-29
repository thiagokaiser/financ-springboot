package com.kaiser.financ.services.impl;

import com.kaiser.financ.dtos.ResetPasswordDTO;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.repositories.UsuarioRepository;
import com.kaiser.financ.security.JWTUtil;
import com.kaiser.financ.services.AuthService;
import com.kaiser.financ.services.EmailService;
import com.kaiser.financ.services.exceptions.AuthorizationException;
import com.kaiser.financ.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private JWTUtil jwtUtil;

  @Autowired
  private BCryptPasswordEncoder pe;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private EmailService emailService;

  @Override
  public void sendResetPassword(String email) {
    UsuarioEntity usuario = usuarioRepository.findByEmail(email);
    if (usuario == null) {
      throw new ObjectNotFoundException("Email nao encontrado");
    }

    String token = jwtUtil.generateTokenResetPassword(email);

    emailService.sendResetPasswordEmail(usuario, token);
  }

  @Override
  public void changePassword(ResetPasswordDTO objDto) {
    if (jwtUtil.tokenValido(objDto.getToken())) {
      String email = jwtUtil.getUsername(objDto.getToken());
      UsuarioEntity usuario = usuarioRepository.findByEmail(email);
      if (usuario == null) {
        throw new ObjectNotFoundException("Email nao encontrado");
      }
      usuario.setSenha(pe.encode(objDto.getNewPassword()));
      usuarioRepository.save(usuario);
    } else {
      throw new AuthorizationException("Token inválido");
    }
  }
}
