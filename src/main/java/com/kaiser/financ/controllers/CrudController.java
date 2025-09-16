package com.kaiser.financ.controllers;

import com.kaiser.financ.entities.BaseEntity;
import com.kaiser.financ.services.CrudService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class CrudController<S extends CrudService, D extends BaseEntity, DTO> {

  @Autowired
  protected S service;

  @GetMapping(value = "/{id}")
  public ResponseEntity<D> find(@PathVariable Integer id) {

    D obj = (D) service.find(id);
    return ResponseEntity.ok().body(obj);
  }

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

  @PutMapping(value = "/{id}")
  public ResponseEntity<Void> update(@Valid @RequestBody DTO objDto, @PathVariable Integer id) {
    D obj = (D) service.fromDTO(objDto);
    obj.setId(id);
    service.update(obj);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<DTO>> findAll() {

    List<D> list = service.findAll();
    List<DTO> listDto = (List<DTO>) list.stream().map(service::toDTO).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }
}
