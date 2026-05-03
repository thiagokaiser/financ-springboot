package com.kaiser.financ.services;

import com.kaiser.financ.entities.DespesaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.List;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

  void sendEmail(SimpleMailMessage msg);

  void sendHtmlEmail(MimeMessage msg);

  void sendResetPasswordEmail(UsuarioEntity usuario, String newPass);

  void sendNotificacaoDespesasEmail(UsuarioEntity usuario, List<DespesaEntity> despesas);

  void sendRelatorioCsvEmail(UsuarioEntity usuario, String csvUrl, LocalDate dtInicial, LocalDate dtFinal);
}
