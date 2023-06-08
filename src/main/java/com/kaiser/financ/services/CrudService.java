package com.kaiser.financ.services;

import com.kaiser.financ.domain.Domain;
import java.util.List;

public interface CrudService<T extends Domain, D> {

    T find(Integer id);

    T insert(T obj);

    T update(T obj);

    void delete(Integer id);

    List<T> findAll();

    T fromDTO(D objDto);

    D toDTO(T obj);

}
