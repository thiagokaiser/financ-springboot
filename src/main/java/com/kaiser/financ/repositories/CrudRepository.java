package com.kaiser.financ.repositories;

import com.kaiser.financ.domain.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface CrudRepository<D> extends JpaRepository<D, Integer>{
	
	@Transactional(readOnly = true) 
	List<D> findByUsuario(Usuario usuario);
	
	@Transactional(readOnly = true)
	Optional<D> findByIdAndUsuario(Integer id,Usuario usuario);
}
