package com.kaiser.financ.services;

import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.dto.ContaDTO;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ContaService {

    Conta find(Integer id);

    Conta insert(Conta obj);

    Conta update(Conta obj);

    void delete(Integer id);

    List<Conta> findAll();

    Page<Conta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String search);

    Conta fromDTO(ContaDTO objDto);

}
