package com.kaiser.financ.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.kaiser.financ.domain.Usuario;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;	
	
	public void sendResetPasswordEmail(Usuario usuario, String linkResetPassword) {		
		try {
			MimeMessage mm = prepareResetPasswordEmail(usuario, linkResetPassword);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			// TODO
		}		
	}

	protected MimeMessage prepareResetPasswordEmail(Usuario usuario, String linkResetPassword) throws MessagingException {
		String link = "http://localhost:4200/security/resetPassword/" + linkResetPassword;
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
}
