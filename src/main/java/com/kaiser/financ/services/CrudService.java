package com.kaiser.financ.services;

import com.kaiser.financ.entities.BaseEntity;
import java.util.List;

public interface CrudService<T extends BaseEntity, D> {

  T find(Integer id);

  T insert(T obj);

  T update(T obj);

  void delete(Integer id);

  List<T> findAll();

  T fromDTO(D objDto);

  D toDTO(T obj);

  void deleteByUsuario(Integer id);
}
