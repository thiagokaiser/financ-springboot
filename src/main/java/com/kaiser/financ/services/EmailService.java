package com.kaiser.financ.services;

import com.kaiser.financ.entities.UsuarioEntity;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

  void sendEmail(SimpleMailMessage msg);

  void sendHtmlEmail(MimeMessage msg);

  void sendResetPasswordEmail(UsuarioEntity usuario, String newPass);
}
