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
		
		Usuario user = new Usuario(null,"Thiago","Kaiser","thiago.kaiser@hotmail.com",pe.encode("123"));		
		Usuario user2 = new Usuario(null,"teste","123","teste@gmail.com",pe.encode("123"));		
		usuarioRepo.saveAll(Arrays.asList(user,user2));
		
		Categoria cat1 = new Categoria(null,  "aaa", "", user);	
		Categoria cat2 = new Categoria(null,  "aaa", "", user);	
		Categoria cat3 = new Categoria(null,  "aaa", "", user);	
		Categoria cat4 = new Categoria(null,  "aaa", "", user);	
		Categoria cat5 = new Categoria(null,  "Moradia Joinville", "#5eaeff", user);	
		Categoria cat6 = new Categoria(null,  "Casamento", "#ff1a1f", user);	
		Categoria cat7 = new Categoria(null,  "Educação", "#84ff84", user);	
		Categoria cat8 = new Categoria(null,  "Pagamentos", "#ef87f8", user);	
		Categoria cat9 = new Categoria(null,  "Teste", "#000000", user);	
		Categoria cat10 = new Categoria(null,  "aaa", "", user);	
		Categoria cat11 = new Categoria(null,  "aaa", "", user);	
		Categoria cat12 = new Categoria(null,  "aaa", "", user);	
		Categoria cat13 = new Categoria(null,  "aaa", "", user);	
		Categoria cat14 = new Categoria(null,  "aaa", "", user);	
		Categoria cat15 = new Categoria(null,  "aaa", "", user);	
		Categoria cat16 = new Categoria(null, "Carro", "#1eeadf", user);	
		Categoria cat17 = new Categoria(null, "Roupa", "#ff00ff", user);	
		Categoria cat18 = new Categoria(null, "Moradia", "#54de58", user);	
		Categoria cat19 = new Categoria(null, "Imposto", "#ff0000", user);	
		Categoria cat20 = new Categoria(null, "Saúde", "#8000ff", user);	
		Categoria cat21 = new Categoria(null, "MEI", "#bb00ff", user);	
		Categoria cat22 = new Categoria(null, "Streaming", "#ff9029", user);	
		Categoria cat23 = new Categoria(null, "Lazer", "#adff33", user);					
		
		categoriaRepo.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7,cat8,cat9,cat10,cat11,cat12,cat13,cat14,cat15,cat16,cat17,cat18,cat19,cat20,cat21,cat22,cat23));		
		
		Conta conta1 = new Conta(null, "Santander", user);
		Conta conta2 = new Conta(null, "Nubank", user);
		Conta conta3 = new Conta(null, "Sabrine", user);
		Conta conta4 = new Conta(null, "Olga", user);
		Conta conta5 = new Conta(null, "Caixa", user);
		Conta conta6 = new Conta(null, "Mercado Pago", user);						
		
		contaRepo.saveAll(Arrays.asList(conta1, conta2,conta3,conta4,conta5,conta6));
		
		
	}
	
}
