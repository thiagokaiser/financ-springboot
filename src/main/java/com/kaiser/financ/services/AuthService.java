package com.kaiser.financ.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.dto.ResetPasswordDTO;
import com.kaiser.financ.repositories.UsuarioRepository;
import com.kaiser.financ.security.JWTUtil;
import com.kaiser.financ.services.exceptions.AuthorizationException;
import com.kaiser.financ.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UsuarioRepository usuarioRepository;	
	
	@Autowired
	private EmailService emailService;		
	
	public void sendResetPassword(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if(usuario == null) {
			throw new ObjectNotFoundException("Email nao encontrado");
		}		
		
		String token = jwtUtil.generateTokenResetPassword(email);		

		emailService.sendResetPasswordEmail(usuario, token);
		
	}	
	
	public void changePassword(ResetPasswordDTO objDto) {
		if(jwtUtil.tokenValido(objDto.getToken())) {
			String email = jwtUtil.getUsername(objDto.getToken());
			Usuario usuario = usuarioRepository.findByEmail(email);
			if(usuario == null) {
				throw new ObjectNotFoundException("Email nao encontrado");
			}			
			usuario.setSenha(pe.encode(objDto.getNewPassword()));		
			usuarioRepository.save(usuario);			
		}else {
			throw new AuthorizationException("Token inválido");
		}		
	}
}
