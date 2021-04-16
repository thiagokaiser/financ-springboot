package com.kaiser.financ.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

	public void insert(Despesa obj) {
		obj.setId(null);
		obj.setCategoria(categoriaService.find(obj.getCategoria().getId()));
		if(obj.getConta() != null) {
			obj.setConta(contaService.find(obj.getConta().getId()));
		}
		
		Integer parcela = 1;
		obj.setParcelaAtual(parcela);
		obj = repo.save(obj);
		
		if(obj.getNumParcelas() > 1) {
			Integer mes = 0;
			
			if(obj.getNumParcelas() > 99) {
				obj.setNumParcelas(99);
			}							
			
			obj.setIdentificador(obj.getId());
			obj = repo.save(obj);
			
			List<Despesa> listDespesas = new ArrayList<Despesa>();
			do {
				parcela += 1;
				mes += 1;
				Despesa despesa = new Despesa();
				updateData(despesa, obj);
				despesa.setIdentificador(obj.getId());
				despesa.setParcelaAtual(parcela);
				despesa.setNumParcelas(obj.getNumParcelas());
				despesa.setUsuario(obj.getUsuario());
				
				Date dtVencimento = obj.getDtVencimento();				
				Calendar cal = Calendar.getInstance();
				cal.setTime(dtVencimento);
				cal.add(Calendar.MONTH, mes);
				dtVencimento = cal.getTime();
				
				despesa.setDtVencimento(dtVencimento);
				listDespesas.add(despesa);
			}while(parcela < obj.getNumParcelas());
			
			repo.saveAll(listDespesas);			
		}				
	}	
	
	public Despesa update(Despesa obj) {
		Despesa newObj = find(obj.getId());
		updateData(newObj, obj);		
		return repo.save(newObj);
	}
	
	public void updateUnpaidByIdentificador(Despesa despesa) {
		if(isNullorZero(despesa.getIdentificador())) {
			throw new DataIntegrityException("Identificador inválido");
		}
		
		Usuario usuario = usuarioService.userLoggedIn();
		List<Despesa> despesas = repo.findByUsuarioAndIdentificadorAndPago(usuario, despesa.getIdentificador(), false);
		
		for (Despesa newDespesa : despesas) {			
			newDespesa.setDescricao(despesa.getDescricao());			
			newDespesa.setValor(despesa.getValor());
			newDespesa.setCategoria(despesa.getCategoria());
			newDespesa.setConta(despesa.getConta());			
		}
		repo.saveAll(despesas);
	}
	
	public void updateAllByIdentificador(Despesa despesa) {
		if(isNullorZero(despesa.getIdentificador())) {
			throw new DataIntegrityException("Identificador inválido");
		}
		
		Usuario usuario = usuarioService.userLoggedIn();
		List<Despesa> despesas = repo.findByUsuarioAndIdentificador(usuario, despesa.getIdentificador());
		
		for (Despesa newDespesa : despesas) {			
			newDespesa.setDescricao(despesa.getDescricao());			
			newDespesa.setCategoria(despesa.getCategoria());						
		}
		repo.saveAll(despesas);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma Despesa que possui Despesas.");			
		}		
	}
	
	public void deleteByIdentificador(Integer identificador) {
			
		if(isNullorZero(identificador)) {
			throw new DataIntegrityException("Identificador inválido");
		}
		
		Usuario usuario = usuarioService.userLoggedIn();		
		List<Despesa> despesas = repo.findByUsuarioAndIdentificadorAndPago(usuario, identificador, false);
		
		try {
			repo.deleteAll(despesas);			
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
						   !this.isNullorZero(objDto.getNumParcelas()) ? objDto.getNumParcelas() : 1, 
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
	
	public boolean isNullorZero(Integer i){
	    return 0 == ( i == null ? 0 : i);
	}
	
}
