package com.kaiser.financ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaiser.financ.domain.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer>{

}
