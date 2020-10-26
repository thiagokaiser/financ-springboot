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
		
		Categoria cat1 = new Categoria(null, "Categ 01", "");
		Categoria cat2 = new Categoria(null, "Categ 02", "");
		Categoria cat3 = new Categoria(null, "Categ 03", "");				
		
		categoriaRepo.saveAll(Arrays.asList(cat1, cat2, cat3));		
		
		Conta conta1 = new Conta(null, "Conta 01");
		Conta conta2 = new Conta(null, "Conta 02");						
		
		contaRepo.saveAll(Arrays.asList(conta1, conta2));
		
		Usuario user = new Usuario(null,"Thiago","kaiserdesenv@gmail.com",pe.encode("123"));
		
		usuarioRepo.save(user);
	}
	
}
