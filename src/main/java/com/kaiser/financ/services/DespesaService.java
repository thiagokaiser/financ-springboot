package com.kaiser.financ.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.domain.Despesa;
import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.dto.DespesaUpdateDTO;
import com.kaiser.financ.repositories.DespesaRepository;
import com.kaiser.financ.services.exceptions.DataIntegrityException;
import com.kaiser.financ.services.exceptions.ObjectNotFoundException;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository repo;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private UsuarioService usuarioService;	
	
	public Despesa find(Integer id) {
		Usuario usuario = usuarioService.userLoggedIn();	
		Optional<Despesa> obj = repo.findByIdAndUsuario(id, usuario);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Despesa.class.getName()));
	}

	public Despesa insert(Despesa obj) {
		obj.setId(null);
		obj.setCategoria(categoriaService.find(obj.getCategoria().getId()));
		if(obj.getConta() != null) {
			obj.setConta(contaService.find(obj.getConta().getId()));
		}
		
		return repo.save(obj);		
	}
	
	public Despesa update(Despesa obj) {
		Despesa newObj = find(obj.getId());
		updateData(newObj, obj);		
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma Despesa que possui Despesas.");			
		}		
	}
	
	public List<Despesa> findAll(){
		Usuario usuario = usuarioService.userLoggedIn();	
		return repo.findByUsuario(usuario);
	}
	
	public Page<Despesa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction,
			                      String search, String stringDtInicial, String stringDtFinal){
		Usuario usuario = usuarioService.userLoggedIn();	
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));		
		Date dtInicial = new Date();
		Date dtFinal = new Date();
		try {
			dtInicial = sdf.parse(stringDtInicial);
			dtFinal = sdf.parse(stringDtFinal);
		} catch (ParseException e) {
			// TODO
		}		 	
		
		return repo.findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqual(usuario, search, dtInicial, dtFinal, pageRequest);		
	}	
	
	public Despesa fromDTO(DespesaUpdateDTO objDto) {		
		Usuario usuario = usuarioService.userLoggedIn();
		Categoria categ = new Categoria();
		categ.setId(objDto.getCategoriaId());
		Conta conta = new Conta();
		if(objDto.getContaId() == null) {
			conta = null;
		}else {
			conta.setId(objDto.getContaId());
		}		
		
		return new Despesa(objDto.getId(), 
						   objDto.getDescricao(), 
						   objDto.getValor(), 
						   objDto.getDtVencimento(), 
						   objDto.getPago(), 
						   objDto.getNumParcelas(), 
						   objDto.getParcelaAtual(), 
						   objDto.getIdentificador(),
						   usuario,
						   categ,
						   conta);						   
	}	
	
	private void updateData(Despesa newObj, Despesa obj) {
		newObj.setDescricao(obj.getDescricao());
		newObj.setCategoria(obj.getCategoria());
		newObj.setConta(obj.getConta());
		newObj.setDtVencimento(obj.getDtVencimento());
		newObj.setPago(obj.getPago());
		newObj.setValor(obj.getValor());
	}
	
}
