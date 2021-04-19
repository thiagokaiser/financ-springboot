package com.kaiser.financ.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.kaiser.financ.domain.Usuario;

public interface EmailService {
	
	//void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	//void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendResetPasswordEmail(Usuario usuario, String newPass);

}
