package com.kaiser.financ.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.repositories.CategoriaRepository;
import com.kaiser.financ.repositories.ContaRepository;
import com.kaiser.financ.repositories.UsuarioRepository;

@Service
public class DBService {
		
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Autowired
	private ContaRepository contaRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	

	public void instantiateTestDatabase() throws ParseException {
		
		Usuario user = new Usuario(null,"Thiago","Kaiser","kaiserdesenv@gmail.com",pe.encode("123"));		
		Usuario user2 = new Usuario(null,"teste","123","teste@gmail.com",pe.encode("123"));		
		usuarioRepo.saveAll(Arrays.asList(user,user2));
		
		Categoria cat1 = new Categoria(null, "Categ 01", "", user);
		Categoria cat2 = new Categoria(null, "Categ 02", "", user2);
		Categoria cat3 = new Categoria(null, "Categ 03", "", user);				
		
		categoriaRepo.saveAll(Arrays.asList(cat1, cat2, cat3));		
		
		Conta conta1 = new Conta(null, "Conta 01", user2);
		Conta conta2 = new Conta(null, "Conta 02", user2);						
		
		contaRepo.saveAll(Arrays.asList(conta1, conta2));
		
		
	}
	
}
