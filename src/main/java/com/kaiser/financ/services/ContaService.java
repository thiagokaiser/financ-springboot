package com.kaiser.financ.services;

import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.dto.ContaDTO;
import org.springframework.data.domain.Page;

public interface ContaService extends CrudService<Conta, ContaDTO> {

  Page<Conta> findPage(
      Integer page, Integer linesPerPage, String orderBy, String direction, String search);
}
