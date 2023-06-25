package com.kaiser.financ.services.impl;

import com.kaiser.financ.dtos.ContaDTO;
import com.kaiser.financ.entities.ContaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.repositories.ContaRepository;
import com.kaiser.financ.services.ContaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ContaServiceImpl extends CrudServiceImpl<ContaEntity, ContaRepository, ContaDTO>
    implements ContaService {

  @Override
  public Page<ContaEntity> findPage(
      Integer page, Integer linesPerPage, String orderBy, String direction, String search) {
    UsuarioEntity usuario = usuarioService.userLoggedIn();
    PageRequest pageRequest =
        PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    return repo.findByUsuarioAndDescricaoContaining(usuario, search, pageRequest);
  }

  @Override
  public ContaEntity fromDTO(ContaDTO objDto) {
    UsuarioEntity usuario = usuarioService.userLoggedIn();
    return new ContaEntity(objDto.getId(), objDto.getDescricao(), usuario);
  }

  @Override
  public ContaDTO toDTO(ContaEntity obj) {
    return new ContaDTO(obj);
  }

  @Override
  protected void updateData(ContaEntity newObj, ContaEntity obj) {
    newObj.setDescricao(obj.getDescricao());
  }
}
