package com.kaiser.financ.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.repositories.UsuarioRepository;
import com.kaiser.financ.security.UserSS;

@Service
public class UsuarioDetalheServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = repo.findByEmail(email);
		if (usuario == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(usuario.getId(), usuario.getEmail(), usuario.getSenha(), usuario.getPerfis(), usuario.getNome(), usuario.getSobrenome());
	}
}
