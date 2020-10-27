package com.kaiser.financ.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.domain.Usuario;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

	@Transactional(readOnly = true)
	Page<Categoria> findByUsuario(Usuario usuario, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	List<Categoria> findByUsuario(Usuario usuario);
	
	@Transactional(readOnly = true)
	Optional<Categoria> findByIdAndUsuario(Integer id,Usuario usuario);
	
}
