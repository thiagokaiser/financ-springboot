package com.kaiser.financ.resources;

import com.kaiser.financ.domain.Domain;
import com.kaiser.financ.services.CrudService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class CrudResource<S extends CrudService, D extends Domain, DTO> {

  @Autowired protected S service;

  @ApiOperation(value = "Find by id")
  @GetMapping(value = "/{id}")
  public ResponseEntity<D> find(@PathVariable Integer id) {

    D obj = (D) service.find(id);
    return ResponseEntity.ok().body(obj);
  }

  @ApiOperation(value = "Insert")
  @PostMapping
  public ResponseEntity<Void> insert(@Valid @RequestBody DTO objDto) {
    D obj = (D) service.fromDTO(objDto);
    obj = (D) service.insert(obj);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(obj.getId())
            .toUri();

    return ResponseEntity.created(uri).build();
  }

  @ApiOperation(value = "Update")
  @PutMapping(value = "/{id}")
  public ResponseEntity<Void> update(@Valid @RequestBody DTO objDto, @PathVariable Integer id) {
    D obj = (D) service.fromDTO(objDto);
    obj.setId(id);
    service.update(obj);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation(value = "Remove")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Não é possível excluir"),
        @ApiResponse(code = 404, message = "Código inexistente")
      })
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation(value = "Find all")
  @GetMapping
  public ResponseEntity<List<DTO>> findAll() {

    List<D> list = service.findAll();
    List<DTO> listDto = (List<DTO>) list.stream().map(service::toDTO).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }
}
