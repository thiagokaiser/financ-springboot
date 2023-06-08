package com.kaiser.financ.services;

import com.kaiser.financ.domain.Despesa;
import com.kaiser.financ.dto.DespesaUpdateDTO;
import com.kaiser.financ.dto.TotaisByCategDTO;
import com.kaiser.financ.dto.TotaisByMonthDTO;
import com.kaiser.financ.dto.TotaisDTO;
import java.util.List;
import org.springframework.data.domain.Page;

public interface DespesaService {

    Despesa update(Despesa obj);

    Despesa find(Integer id);

    void insert(Despesa obj);

    void updateUnpaidByIdParcela(Despesa despesa);

    void updateAllByIdParcela(Despesa despesa);

    void delete(Integer id);

    void deleteByIdParcela(Integer idParcela);

    List<Despesa> findAll();

    Page<Despesa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String search, String stringDtInicial, String stringDtFinal, Boolean pago);

    TotaisDTO totalsByPeriod(String stringDtInicial, String stringDtFinal, String search);

    List<TotaisByCategDTO> totalsByPeriodByCategoria(String stringDtInicial, String stringDtFinal, String search);

    List<TotaisByMonthDTO> totalsByPeriodByMonth(String stringDtInicial, String stringDtFinal, String search);

    Despesa fromDTO(DespesaUpdateDTO objDto);

}
