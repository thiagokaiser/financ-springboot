package com.kaiser.financ.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.dto.ContaDTO;
import com.kaiser.financ.repositories.ContaRepository;
import com.kaiser.financ.services.ContaService;
import com.kaiser.financ.services.UsuarioService;
import com.kaiser.financ.services.exceptions.DataIntegrityException;
import com.kaiser.financ.services.exceptions.ObjectNotFoundException;

@Service
public class ContaServiceImpl implements ContaService{

	@Autowired
	private ContaRepository repo;
	
	@Autowired
	private UsuarioService usuarioService;	
	
	@Override
	public Conta find(Integer id) {
		Usuario usuario = usuarioService.userLoggedIn();
		Optional<Conta> obj = repo.findByIdAndUsuario(id, usuario);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Conta.class.getName()));
	}

	@Override
	public Conta insert(Conta obj) {
		obj.setId(null);
		return repo.save(obj);		
	}
	
	@Override
	public Conta update(Conta obj) {
		Conta newObj = find(obj.getId());
		updateData(newObj, obj);		
		return repo.save(newObj);
	}
	
	@Override
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma conta que possui Despesas.");			
		}		
	}
	
	@Override
	public List<Conta> findAll(){
		Usuario usuario = usuarioService.userLoggedIn();
		return repo.findByUsuario(usuario);
	}
	
	@Override
	public Page<Conta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String search){
		Usuario usuario = usuarioService.userLoggedIn();
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
		return repo.findByUsuarioAndDescricaoContaining(usuario, search, pageRequest);		
	}	
	
	@Override
	public Conta fromDTO(ContaDTO objDto) {
		Usuario usuario = usuarioService.userLoggedIn();
		return new Conta(objDto.getId(), objDto.getDescricao(), usuario);
	}
	
	private void updateData(Conta newObj, Conta obj) {
		newObj.setDescricao(obj.getDescricao());
	}
	
}
