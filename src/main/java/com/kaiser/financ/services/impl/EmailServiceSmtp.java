package com.kaiser.financ.services.impl;

import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceSmtp extends EmailServiceAbstract {

  private static final Logger LOG = LoggerFactory.getLogger(EmailServiceSmtp.class);
  @Autowired
  private MailSender mailSender;
  @Autowired
  private JavaMailSender javaMailSender;

  @Override
  public void sendEmail(SimpleMailMessage msg) {
    LOG.info("Enviando email...");
    mailSender.send(msg);
    LOG.info("Email enviado");
  }

  @Override
  public void sendHtmlEmail(MimeMessage msg) {
    LOG.info("Enviando email Html...");
    javaMailSender.send(msg);
    LOG.info("Email enviado");
  }
}
