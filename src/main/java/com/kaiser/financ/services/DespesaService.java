package com.kaiser.financ.services;

import com.kaiser.financ.dtos.DespesaDTO;
import com.kaiser.financ.dtos.TotaisByCategDTO;
import com.kaiser.financ.dtos.TotaisByMonthDTO;
import com.kaiser.financ.dtos.TotaisDTO;
import com.kaiser.financ.entities.DespesaEntity;
import java.util.List;
import org.springframework.data.domain.Page;

public interface DespesaService extends CrudService<DespesaEntity, DespesaDTO> {

  void updateUnpaidByIdParcela(DespesaEntity despesa);

  void updateAllByIdParcela(DespesaEntity despesa);

  void deleteByIdParcela(Integer idParcela);

  Page<DespesaEntity> findPage(
      Integer page,
      Integer linesPerPage,
      String orderBy,
      String direction,
      String search,
      String stringDtInicial,
      String stringDtFinal,
      Boolean pago);

  TotaisDTO totalsByPeriod(String stringDtInicial, String stringDtFinal, String search);

  List<TotaisByCategDTO> totalsByPeriodByCategoria(
      String stringDtInicial, String stringDtFinal, String search);

  List<TotaisByMonthDTO> totalsByPeriodByMonth(
      String stringDtInicial, String stringDtFinal, String search);
}
