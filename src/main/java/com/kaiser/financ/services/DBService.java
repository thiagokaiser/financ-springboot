package com.kaiser.financ.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.repositories.CategoriaRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private CategoriaRepository categoriaRepo;	
	

	public void instantiateTestDatabase() throws ParseException {	
		
		Categoria cat1 = new Categoria(null, "Categ 01", "");
		Categoria cat2 = new Categoria(null, "Categ 02", "");
		Categoria cat3 = new Categoria(null, "Categ 03", "");				
		
		categoriaRepo.saveAll(Arrays.asList(cat1, cat2, cat3));		
		
		
	}
	
}
