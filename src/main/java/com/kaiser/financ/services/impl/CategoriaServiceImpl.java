package com.kaiser.financ.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.dto.CategoriaDTO;
import com.kaiser.financ.repositories.CategoriaRepository;
import com.kaiser.financ.services.CategoriaService;
import com.kaiser.financ.services.NotificacaoService;
import com.kaiser.financ.services.UsuarioService;
import com.kaiser.financ.services.exceptions.DataIntegrityException;
import com.kaiser.financ.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	private CategoriaRepository repo;
	
	@Autowired
	private UsuarioService usuarioService;	
	
	@Autowired
    private NotificacaoService notificacaoService;
	
	@Override
	public Categoria find(Integer id) {
		Usuario usuario = usuarioService.userLoggedIn();	
		Optional<Categoria> obj = repo.findByIdAndUsuario(id, usuario);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	@Override
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);		
	}
	
	@Override
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());		
		updateData(newObj, obj);	
		return repo.save(newObj);
	}
	
	@Override
	public void delete(Integer id) {
		find(id);				
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui Despesas.");			
		}		
	}
	
	@Override
	public List<Categoria> findAll(){
	    Usuario usuario = usuarioService.userLoggedIn();
	    notificacaoService.insert(usuario);
		return repo.findByUsuario(usuario);
	}	
	
	@Override
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String search){
		Usuario usuario = usuarioService.userLoggedIn();
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findByUsuarioAndDescricaoContaining(usuario, search, pageRequest);
	}	
	
	@Override
	public Categoria fromDTO(CategoriaDTO objDto) {				
		Usuario usuario = usuarioService.userLoggedIn();		
		return new Categoria(objDto.getId(), objDto.getDescricao(), objDto.getCor(), usuario);
	}
		
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setDescricao(obj.getDescricao());
		newObj.setCor(obj.getCor());
	}
	
}
