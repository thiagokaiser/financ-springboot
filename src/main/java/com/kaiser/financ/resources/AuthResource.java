package com.kaiser.financ.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kaiser.financ.dto.EmailDTO;
import com.kaiser.financ.security.JWTUtil;
import com.kaiser.financ.security.UserSS;
import com.kaiser.financ.services.AuthService;
import com.kaiser.financ.services.UsuarioService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<String> refreshToken(HttpServletResponse response) {
		UserSS user = UsuarioService.authenticated();
		String token = jwtUtil.generateToken(user);
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		String body = "{\"email\":\"" + user.getUsername() + "\",\"accessToken\":\"" + token + "\"}";
		return ResponseEntity.ok().body(body);
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}
