package com.kaiser.financ.services;

import com.kaiser.financ.domain.Usuario;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

  void sendEmail(SimpleMailMessage msg);

  void sendHtmlEmail(MimeMessage msg);

  void sendResetPasswordEmail(Usuario usuario, String newPass);
}
