package com.kaiser.financ.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.dto.ContaDTO;
import com.kaiser.financ.services.ContaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/contas")
public class ContaResource {

	@Autowired
	private ContaService service;	
	
	@ApiOperation(value = "Busca por id")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Conta> find(@PathVariable Integer id) {
		
		Conta obj = service.find(id);		
		return ResponseEntity.ok().body(obj);		
		
	}
	
	@ApiOperation(value = "Insere conta")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ContaDTO objDto){		
		Conta obj = service.fromDTO(objDto);		
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Atualiza conta")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ContaDTO objDto, @PathVariable Integer id){
		Conta obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Remove conta")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"),
			@ApiResponse(code = 404, message = "Código inexistente") })
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		service.delete(id);		
		return ResponseEntity.noContent().build();		
		
	}
	
	@ApiOperation(value = "Retorna todas contas")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ContaDTO>> findAll() {
		
		List<Conta> list = service.findAll();
		List<ContaDTO> listDto = list.stream().map(obj -> new ContaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);		
		
	}
	
	@ApiOperation(value = "Retorna todas contas com paginação")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ContaDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "descricao") String orderBy, 
			@RequestParam(value="direction", defaultValue = "DESC") String direction) {
		
		Page<Conta> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ContaDTO> listDto = list.map(obj -> new ContaDTO(obj));
		return ResponseEntity.ok().body(listDto);		
		
	}
	
	
}
