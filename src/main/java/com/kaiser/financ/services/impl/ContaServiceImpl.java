package com.kaiser.financ.services.impl;

import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.dto.ContaDTO;
import com.kaiser.financ.repositories.ContaRepository;
import com.kaiser.financ.services.ContaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ContaServiceImpl extends CrudServiceImpl<Conta, ContaRepository, ContaDTO>
    implements ContaService {

  @Override
  public Page<Conta> findPage(
      Integer page, Integer linesPerPage, String orderBy, String direction, String search) {
    Usuario usuario = usuarioService.userLoggedIn();
    PageRequest pageRequest =
        PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    return repo.findByUsuarioAndDescricaoContaining(usuario, search, pageRequest);
  }

  @Override
  public Conta fromDTO(ContaDTO objDto) {
    Usuario usuario = usuarioService.userLoggedIn();
    return new Conta(objDto.getId(), objDto.getDescricao(), usuario);
  }

  @Override
  public ContaDTO toDTO(Conta obj) {
    return new ContaDTO(obj);
  }

  @Override
  protected void updateData(Conta newObj, Conta obj) {
    newObj.setDescricao(obj.getDescricao());
  }
}
