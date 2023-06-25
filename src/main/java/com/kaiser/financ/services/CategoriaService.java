package com.kaiser.financ.services;

import com.kaiser.financ.dtos.CategoriaDTO;
import com.kaiser.financ.entities.CategoriaEntity;
import org.springframework.data.domain.Page;

public interface CategoriaService extends CrudService<CategoriaEntity, CategoriaDTO> {

  Page<CategoriaEntity> findPage(
      Integer page, Integer linesPerPage, String orderBy, String direction, String search);
}
