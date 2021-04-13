package com.kaiser.financ.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.domain.Usuario;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer>{
	
	@Transactional(readOnly = true)
	Page<Conta> findByUsuarioAndDescricaoContaining(Usuario usuario, String search, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	List<Conta> findByUsuario(Usuario usuario);
	
	@Transactional(readOnly = true)
	Optional<Conta> findByIdAndUsuario(Integer id,Usuario usuario);
}
