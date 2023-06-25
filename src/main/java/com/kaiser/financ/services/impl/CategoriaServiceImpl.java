package com.kaiser.financ.services.impl;

import com.kaiser.financ.dtos.CategoriaDTO;
import com.kaiser.financ.entities.CategoriaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.repositories.CategoriaRepository;
import com.kaiser.financ.services.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl
    extends CrudServiceImpl<CategoriaEntity, CategoriaRepository, CategoriaDTO>
    implements CategoriaService {

  @Override
  public Page<CategoriaEntity> findPage(
      Integer page, Integer linesPerPage, String orderBy, String direction, String search) {
    UsuarioEntity usuario = usuarioService.userLoggedIn();
    PageRequest pageRequest =
        PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    return repo.findByUsuarioAndDescricaoContaining(usuario, search, pageRequest);
  }

  @Override
  public CategoriaEntity fromDTO(CategoriaDTO objDto) {
    UsuarioEntity usuario = usuarioService.userLoggedIn();
    return new CategoriaEntity(objDto.getId(), objDto.getDescricao(), objDto.getCor(), usuario);
  }

  @Override
  public CategoriaDTO toDTO(CategoriaEntity obj) {
    return new CategoriaDTO(obj);
  }

  @Override
  protected void updateData(CategoriaEntity newObj, CategoriaEntity obj) {
    newObj.setDescricao(obj.getDescricao());
    newObj.setCor(obj.getCor());
  }
}
