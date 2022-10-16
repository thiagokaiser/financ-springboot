package com.kaiser.financ.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.domain.enums.Perfil;
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
		
		Usuario user = new Usuario(null,"user","sobrenome","user@teste.com",pe.encode("123"));
		user.addPerfil(Perfil.USER);
		Usuario user2 = new Usuario(null,"admin","sobrenome","admin@teste.com",pe.encode("123"));
		user2.addPerfil(Perfil.ADMIN);
		usuarioRepo.saveAll(Arrays.asList(user,user2));
		
		Categoria cat1 = new Categoria(null,  "Categ 01", "#5eaeff", user);	
		Categoria cat2 = new Categoria(null,  "Categ 02", "#ff1a1f", user);	
		Categoria cat3 = new Categoria(null,  "Categ 03", "#84ff84", user2);	
		Categoria cat4 = new Categoria(null,  "Categ 04", "#ef87f8", user2);							
		
		categoriaRepo.saveAll(Arrays.asList(cat1,cat2,cat3,cat4));		
		
		Conta conta1 = new Conta(null, "Conta 01", user);
		Conta conta2 = new Conta(null, "Conta 02", user);
		Conta conta3 = new Conta(null, "Conta 03", user2);
		Conta conta4 = new Conta(null, "Conta 04", user2);								
		
		contaRepo.saveAll(Arrays.asList(conta1, conta2,conta3,conta4));		
		
	}	
}
