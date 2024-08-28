package com.kaiser.financ.repositories;

import com.kaiser.financ.dtos.TotaisByCategDTO;
import com.kaiser.financ.dtos.TotaisByMonthDTO;
import com.kaiser.financ.entities.DespesaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DespesaRepository extends CrudRepository<DespesaEntity> {

  @Transactional(readOnly = true)
  Page<DespesaEntity>
      findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqual(
          UsuarioEntity usuario, String search, LocalDate dtInicial, LocalDate dtFinal, Pageable pageRequest);

  @Transactional(readOnly = true)
  Page<DespesaEntity>
      findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqualAndPago(
          UsuarioEntity usuario,
          String search,
          LocalDate dtInicial,
          LocalDate dtFinal,
          Pageable pageRequest,
          Boolean pago);

  @Transactional(readOnly = true)
  List<DespesaEntity>
      findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqual(
          UsuarioEntity usuario, String search, LocalDate dtInicial, LocalDate dtFinal);

  @Transactional(readOnly = true)
  List<DespesaEntity> findByUsuarioAndIdParcelaAndPago(UsuarioEntity usuario, Integer idParcela, Boolean pago);

  @Transactional(readOnly = true)
  List<DespesaEntity> findByUsuarioAndIdParcela(UsuarioEntity usuario, Integer idParcela);

  @Transactional(readOnly = true)
  @Query(
      value =
          "SELECT new com.kaiser.financ.dtos.TotaisByCategDTO(categ.descricao, categ.cor, SUM(desp.valor)) FROM Despesa desp"
              + " INNER JOIN desp.categoria categ"
              + " WHERE desp.usuario = ?1 AND desp.dtVencimento >= ?2 AND desp.dtVencimento <= ?3"
              + " GROUP BY categ.descricao, categ.cor")
  List<TotaisByCategDTO> totalsByPeriodByCategoria(UsuarioEntity usuario, LocalDate dtInicial, LocalDate dtFinal);

  @Transactional(readOnly = true)
  @Query(
      value =
          "SELECT new com.kaiser.financ.dtos.TotaisByMonthDTO(month(desp.dtVencimento), year(desp.dtVencimento), sum(desp.valor)) FROM Despesa desp"
              + " INNER JOIN desp.categoria categ"
              + " WHERE desp.usuario = ?1 AND desp.dtVencimento >= ?2 AND desp.dtVencimento <= ?3 AND desp.pago = true"
              + " GROUP BY month(desp.dtVencimento), year(desp.dtVencimento)"
              + " ORDER BY year(desp.dtVencimento) DESC, month(desp.dtVencimento) DESC")
  List<TotaisByMonthDTO> totalsByPeriodByMonth(UsuarioEntity usuario, LocalDate dtInicial, LocalDate dtFinal);
}
