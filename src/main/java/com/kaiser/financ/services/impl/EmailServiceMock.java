package com.kaiser.financ.services.impl;

import com.kaiser.financ.entities.DespesaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class EmailServiceMock extends EmailServiceAbstract {

  private static final Logger LOG = LoggerFactory.getLogger(EmailServiceMock.class);

  @Override
  public void sendEmail(SimpleMailMessage msg) {
    LOG.info("Simulando envio de email...");
    LOG.info(msg.toString());
    LOG.info("Email enviado");
  }

  @Override
  public void sendHtmlEmail(MimeMessage msg) {
    LOG.info("Simulando envio de email HTML...");
    LOG.info(msg.toString());
    LOG.info("Email enviado");
  }

  @Override
  public void sendNotificacaoDespesasEmail(UsuarioEntity usuario, List<DespesaEntity> despesas) {
    LOG.info("Simulando envio de email de notificação para: {}", usuario.getEmail());
    LOG.info("Despesas pendentes: {}", despesas.size());
    despesas.forEach(d -> LOG.info("  - {} | R$ {} | Venc: {}", d.getDescricao(), d.getValor(), d.getDtVencimento()));
    LOG.info("Email de notificação enviado");
  }
}
