package com.kaiser.financ.services.impl;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.dto.CategoriaDTO;
import com.kaiser.financ.repositories.CategoriaRepository;
import com.kaiser.financ.services.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl
    extends CrudServiceImpl<Categoria, CategoriaRepository, CategoriaDTO>
    implements CategoriaService {

  @Override
  public Page<Categoria> findPage(
      Integer page, Integer linesPerPage, String orderBy, String direction, String search) {
    Usuario usuario = usuarioService.userLoggedIn();
    PageRequest pageRequest =
        PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    return repo.findByUsuarioAndDescricaoContaining(usuario, search, pageRequest);
  }

  @Override
  public Categoria fromDTO(CategoriaDTO objDto) {
    Usuario usuario = usuarioService.userLoggedIn();
    return new Categoria(objDto.getId(), objDto.getDescricao(), objDto.getCor(), usuario);
  }

  @Override
  public CategoriaDTO toDTO(Categoria obj) {
    return new CategoriaDTO(obj);
  }

  @Override
  protected void updateData(Categoria newObj, Categoria obj) {
    newObj.setDescricao(obj.getDescricao());
    newObj.setCor(obj.getCor());
  }
}
