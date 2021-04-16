package com.kaiser.financ.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kaiser.financ.domain.Despesa;
import com.kaiser.financ.domain.Usuario;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Integer>{

	@Transactional(readOnly = true)
	Page<Despesa> findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqual(Usuario usuario, String search, Date dtInicial, Date dtFinal, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	List<Despesa> findByUsuario(Usuario usuario);
	
	@Transactional(readOnly = true)
	Optional<Despesa> findByIdAndUsuario(Integer id,Usuario usuario);
	
	@Transactional(readOnly = true)
	List<Despesa> findByUsuarioAndIdentificadorAndPago(Usuario usuario, Integer identificador, Boolean pago);
	
	@Transactional(readOnly = true)
	List<Despesa> findByUsuarioAndIdentificador(Usuario usuario, Integer identificador);
	
	
}
