package com.kaiser.financ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaiser.financ.domain.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Integer>{

}
