package com.kaiser.financ.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.dto.CategoriaDTO;

public interface CategoriaService {

    Categoria find(Integer id);

    Categoria insert(Categoria obj);

    Categoria update(Categoria obj);

    void delete(Integer id);

    List<Categoria> findAll();

    Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String search);

    Categoria fromDTO(CategoriaDTO objDto);

}
