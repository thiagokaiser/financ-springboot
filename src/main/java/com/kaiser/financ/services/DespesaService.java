package com.kaiser.financ.services;

import com.kaiser.financ.domain.Despesa;
import com.kaiser.financ.dto.DespesaDTO;
import com.kaiser.financ.dto.TotaisByCategDTO;
import com.kaiser.financ.dto.TotaisByMonthDTO;
import com.kaiser.financ.dto.TotaisDTO;
import java.util.List;
import org.springframework.data.domain.Page;

public interface DespesaService extends CrudService<Despesa, DespesaDTO>{

    void updateUnpaidByIdParcela(Despesa despesa);

    void updateAllByIdParcela(Despesa despesa);

    void deleteByIdParcela(Integer idParcela); 

    Page<Despesa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String search, String stringDtInicial, String stringDtFinal, Boolean pago);

    TotaisDTO totalsByPeriod(String stringDtInicial, String stringDtFinal, String search);

    List<TotaisByCategDTO> totalsByPeriodByCategoria(String stringDtInicial, String stringDtFinal, String search);

    List<TotaisByMonthDTO> totalsByPeriodByMonth(String stringDtInicial, String stringDtFinal, String search);

}
