package com.kaiser.financ.services.impl;

import com.kaiser.financ.entities.DespesaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public abstract class EmailServiceAbstract implements EmailService {

  @Value("${default.sender}")
  private String sender;

  @Value("${default.frontend_url}")
  private String frontEndUrl;

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private JavaMailSender javaMailSender;

  public void sendResetPasswordEmail(UsuarioEntity usuario, String linkResetPassword) {
    try {
      MimeMessage mm = prepareResetPasswordEmail(usuario, linkResetPassword);
      sendHtmlEmail(mm);
    } catch (MessagingException e) {
      // TODO
    }
  }

  protected MimeMessage prepareResetPasswordEmail(UsuarioEntity usuario, String linkResetPassword)
      throws MessagingException {
    String link = frontEndUrl + "security/resetPassword/" + linkResetPassword;
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);

    mmh.setTo(usuario.getEmail());
    mmh.setFrom(sender);
    mmh.setSubject("Reset Password - Financ");
    mmh.setSentDate(new Date(System.currentTimeMillis()));
    mmh.setText(htmlFromTemplatePedido(link), true);
    return mimeMessage;
  }

  protected String htmlFromTemplatePedido(String link) {
    Context context = new Context();
    context.setVariable("link", link);
    return templateEngine.process("email/resetPassword", context);
  }

  @Override
  public void sendNotificacaoDespesasEmail(UsuarioEntity usuario, List<DespesaEntity> despesas) {
    try {
      MimeMessage mm = prepareNotificacaoDespesasEmail(usuario, despesas);
      sendHtmlEmail(mm);
    } catch (MessagingException e) {
      // TODO
    }
  }

  protected MimeMessage prepareNotificacaoDespesasEmail(UsuarioEntity usuario, List<DespesaEntity> despesas)
      throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);

    mmh.setTo(usuario.getEmail());
    mmh.setFrom(sender);
    mmh.setSubject("Lembrete de despesas - Financ");
    mmh.setSentDate(new Date(System.currentTimeMillis()));
    mmh.setText(htmlFromTemplateNotificacao(usuario, despesas), true);
    return mimeMessage;
  }

  protected String htmlFromTemplateNotificacao(UsuarioEntity usuario, List<DespesaEntity> despesas) {
    Context context = new Context();
    context.setVariable("usuario", usuario);
    context.setVariable("despesas", despesas);
    return templateEngine.process("email/notificacaoDespesas", context);
  }
}
