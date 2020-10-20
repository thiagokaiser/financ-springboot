package com.kaiser.financ.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.kaiser.financ.domain.Despesa;
import com.kaiser.financ.dto.DespesaDTO;
import com.kaiser.financ.repositories.DespesaRepository;
import com.kaiser.financ.services.exceptions.DataIntegrityException;
import com.kaiser.financ.services.exceptions.ObjectNotFoundException;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository repo;
	
	public Despesa find(Integer id) {
		Optional<Despesa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Despesa.class.getName()));
	}

	public Despesa insert(Despesa obj) {
		obj.setId(null);
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
		return repo.findAll();
	}
	
	public Page<Despesa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);		
	}	
	
	public Despesa fromDTO(DespesaDTO objDto) {		
		return new Despesa(objDto.getId(), 
						   objDto.getDescricao(), 
						   objDto.getValor(), 
						   objDto.getDtVencimento(), 
						   objDto.getPago(), 
						   objDto.getNumParcelas(), 
						   objDto.getParcelaAtual(), 
						   objDto.getIdentificador());						   
	}
	
	private void updateData(Despesa newObj, Despesa obj) {
		newObj.setDescricao(obj.getDescricao());		
	}
	
}
