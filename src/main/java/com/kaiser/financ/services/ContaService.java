package com.kaiser.financ.services;

import com.kaiser.financ.dtos.ContaDTO;
import com.kaiser.financ.entities.ContaEntity;
import org.springframework.data.domain.Page;

public interface ContaService extends CrudService<ContaEntity, ContaDTO> {

  Page<ContaEntity> findPage(
      Integer page, Integer linesPerPage, String orderBy, String direction, String search);
}
