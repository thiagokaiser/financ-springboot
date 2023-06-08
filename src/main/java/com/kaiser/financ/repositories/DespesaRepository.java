package com.kaiser.financ.repositories;

import com.kaiser.financ.domain.Despesa;
import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.dto.TotaisByCategDTO;
import com.kaiser.financ.dto.TotaisByMonthDTO;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Integer>{

	@Transactional(readOnly = true)
	Page<Despesa> findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqual(Usuario usuario, String search, Date dtInicial, Date dtFinal, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	Page<Despesa> findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqualAndPago(Usuario usuario, String search, Date dtInicial, Date dtFinal, Pageable pageRequest, Boolean pago);
	
	@Transactional(readOnly = true)
	List<Despesa> findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqual(Usuario usuario, String search, Date dtInicial, Date dtFinal);
	
	@Transactional(readOnly = true)
	List<Despesa> findByUsuario(Usuario usuario);
	
	@Transactional(readOnly = true)
	Optional<Despesa> findByIdAndUsuario(Integer id,Usuario usuario);
	
	@Transactional(readOnly = true)
	List<Despesa> findByUsuarioAndIdParcelaAndPago(Usuario usuario, Integer idParcela, Boolean pago);
	
	@Transactional(readOnly = true)
	List<Despesa> findByUsuarioAndIdParcela(Usuario usuario, Integer idParcela);	
		
	@Transactional(readOnly = true)
	@Query(value = 
		"SELECT new com.kaiser.financ.dto.TotaisByCategDTO(categ.descricao, categ.cor, SUM(desp.valor)) FROM Despesa desp" +
		" INNER JOIN desp.categoria categ" +
		" WHERE desp.usuario = ?1 AND desp.dtVencimento >= ?2 AND desp.dtVencimento <= ?3" +
		" GROUP BY categ.descricao, categ.cor")			
	List<TotaisByCategDTO> totalsByPeriodByCategoria(Usuario usuario, Date dtInicial, Date dtFinal);
	
	@Transactional(readOnly = true)
	@Query(value = 
		"SELECT new com.kaiser.financ.dto.TotaisByMonthDTO(month(desp.dtVencimento), year(desp.dtVencimento), sum(desp.valor)) FROM Despesa desp" +
		" INNER JOIN desp.categoria categ" +
		" WHERE desp.usuario = ?1 AND desp.dtVencimento >= ?2 AND desp.dtVencimento <= ?3 AND desp.pago = true" +
		" GROUP BY month(desp.dtVencimento), year(desp.dtVencimento)" +
		" ORDER BY year(desp.dtVencimento) DESC, month(desp.dtVencimento) DESC")			
	List<TotaisByMonthDTO> totalsByPeriodByMonth(Usuario usuario, Date dtInicial, Date dtFinal);
	
}
