package com.kaiser.financ.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@GetMapping(value="/{id}")
	public ResponseEntity<Conta> find(@PathVariable Integer id) {
		
		Conta obj = service.find(id);		
		return ResponseEntity.ok().body(obj);		
		
	}
	
	@ApiOperation(value = "Insere conta")	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ContaDTO objDto){		
		Conta obj = service.fromDTO(objDto);		
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Atualiza conta")	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ContaDTO objDto, @PathVariable Integer id){
		Conta obj = service.fromDTO(objDto);
		obj.setId(id);
		service.update(obj);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Remove conta")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Não é possível excluir uma conta que possui despesas"),
			@ApiResponse(code = 404, message = "Código inexistente") })	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		service.delete(id);		
		return ResponseEntity.noContent().build();		
		
	}
	
	@ApiOperation(value = "Retorna todas contas")	
	@GetMapping
	public ResponseEntity<List<ContaDTO>> findAll() {
		
		List<Conta> list = service.findAll();
		List<ContaDTO> listDto = list.stream().map(ContaDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);		
		
	}
	
	@ApiOperation(value = "Retorna todas contas com paginação")	
	@GetMapping(value="/page")
	public ResponseEntity<Page<ContaDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "descricao") String orderBy, 
			@RequestParam(value="direction", defaultValue = "DESC") String direction,
			@RequestParam(value="search") String search) {
		
		Page<Conta> list = service.findPage(page, linesPerPage, orderBy, direction, search);
		Page<ContaDTO> listDto = list.map(ContaDTO::new);
		return ResponseEntity.ok().body(listDto);		
		
	}
	
	
}
