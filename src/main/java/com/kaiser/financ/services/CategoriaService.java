package com.kaiser.financ.services;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.dto.CategoriaDTO;
import org.springframework.data.domain.Page;

public interface CategoriaService extends CrudService<Categoria, CategoriaDTO> {

  Page<Categoria> findPage(
      Integer page, Integer linesPerPage, String orderBy, String direction, String search);
}
