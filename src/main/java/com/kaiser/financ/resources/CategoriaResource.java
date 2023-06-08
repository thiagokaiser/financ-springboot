package com.kaiser.financ.resources;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.dto.CategoriaDTO;
import com.kaiser.financ.services.CategoriaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@ApiOperation(value = "Busca por id")	
	@GetMapping(value="/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		Categoria obj = service.find(id);		
		return ResponseEntity.ok().body(obj);		
		
	}
	
	@ApiOperation(value = "Insere categoria")	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){		
		Categoria obj = service.fromDTO(objDto);		
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Atualiza categoria")	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		service.update(obj);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Remove categoria")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui despesas"),
			@ApiResponse(code = 404, message = "Código inexistente") })	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		service.delete(id);		
		return ResponseEntity.noContent().build();		
		
	}
	
	@ApiOperation(value = "Retorna todas categorias")	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
	    
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDto = list.stream().map(CategoriaDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);		
		
	}
	
	@ApiOperation(value = "Retorna todas categorias com paginação")	
	@GetMapping(value="/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "descricao") String orderBy, 
			@RequestParam(value="direction", defaultValue = "DESC") String direction,
			@RequestParam(value="search") String search) {
		
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction, search);
		Page<CategoriaDTO> listDto = list.map(CategoriaDTO::new);
		return ResponseEntity.ok().body(listDto);		
		
	}	
}
