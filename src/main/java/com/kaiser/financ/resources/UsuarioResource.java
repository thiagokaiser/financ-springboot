package com.kaiser.financ.resources;

import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.dto.FileUploadDTO;
import com.kaiser.financ.dto.UsuarioDTO;
import com.kaiser.financ.dto.UsuarioNewDTO;
import com.kaiser.financ.dto.UsuarioUpdateAdminDTO;
import com.kaiser.financ.dto.UsuarioUpdateDTO;
import com.kaiser.financ.services.UsuarioService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

  @Autowired private UsuarioService service;

  @GetMapping(value = "/{id}")
  public ResponseEntity<UsuarioDTO> find(@PathVariable Integer id) {
    Usuario obj = service.find(id);
    UsuarioDTO objDto = new UsuarioDTO(obj);
    return ResponseEntity.ok().body(objDto);
  }

  @GetMapping(value = "/email/{email}")
  public ResponseEntity<UsuarioDTO> find(@PathVariable String email) {
    Usuario obj = service.findByEmail(email);
    UsuarioDTO objDto = new UsuarioDTO(obj);
    return ResponseEntity.ok().body(objDto);
  }

  @PostMapping
  public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNewDTO objDto) {
    Usuario obj = service.fromDTO(objDto);
    obj = service.insert(obj);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(obj.getId())
            .toUri();

    return ResponseEntity.created(uri).build();
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Void> update(
      @Valid @RequestBody UsuarioUpdateDTO objDto, @PathVariable Integer id) {
    Usuario obj = service.fromDTO(objDto);
    obj.setId(id);
    service.update(obj);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/picture")
  public ResponseEntity<UsuarioDTO> uploadProfilePicture(@ModelAttribute FileUploadDTO objDto) {
    UsuarioDTO usuarioDto = service.uploadProfilePicture(objDto.getFile());
    return ResponseEntity.ok().body(usuarioDto);
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {

    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<UsuarioDTO>> findAll() {
    List<Usuario> list = service.findAll();
    List<UsuarioDTO> listDto = list.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping(value = "/page")
  public ResponseEntity<Page<UsuarioDTO>> findPage(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
      @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
      @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

    Page<Usuario> list = service.findPage(page, linesPerPage, orderBy, direction);
    Page<UsuarioDTO> listDto = list.map(UsuarioDTO::new);
    return ResponseEntity.ok().body(listDto);
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PutMapping(value = "/admin/{id}")
  public ResponseEntity<Void> updateAdmin(
      @Valid @RequestBody UsuarioUpdateAdminDTO objDto, @PathVariable Integer id) {
    Usuario obj = service.fromDTO(objDto);
    obj.setId(id);
    service.updateAdmin(obj);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping(value = "/removePerfil/{usuarioId}/{perfil}")
  public ResponseEntity<Void> removePerfil(
      @PathVariable Integer usuarioId, @PathVariable String perfil) {
    service.removePerfil(usuarioId, perfil);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PostMapping(value = "/addPerfil/{usuarioId}/{perfil}")
  public ResponseEntity<Void> addPerfil(
      @PathVariable Integer usuarioId, @PathVariable String perfil) {
    service.addPerfil(usuarioId, perfil);
    return ResponseEntity.noContent().build();
  }
}
