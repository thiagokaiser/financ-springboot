package com.kaiser.financ.services.impl;

import com.kaiser.financ.dtos.DespesaDTO;
import com.kaiser.financ.dtos.TotaisByCategDTO;
import com.kaiser.financ.dtos.TotaisByMonthDTO;
import com.kaiser.financ.dtos.TotaisDTO;
import com.kaiser.financ.entities.CategoriaEntity;
import com.kaiser.financ.entities.ContaEntity;
import com.kaiser.financ.entities.DespesaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.repositories.DespesaRepository;
import com.kaiser.financ.services.CategoriaService;
import com.kaiser.financ.services.ContaService;
import com.kaiser.financ.services.DespesaService;
import com.kaiser.financ.services.exceptions.DataIntegrityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class DespesaServiceImpl extends CrudServiceImpl<DespesaEntity, DespesaRepository, DespesaDTO>
    implements DespesaService {

  @Autowired
  private CategoriaService categoriaService;
  @Autowired
  private ContaService contaService;

  @Override
  public DespesaEntity insert(DespesaEntity obj) {
    obj.setId(null);
    obj.setCategoria(categoriaService.find(obj.getCategoria().getId()));
    if (obj.getConta() != null) {
      obj.setConta(contaService.find(obj.getConta().getId()));
    }

    obj.setParcelaAtual(1);
    obj = repo.save(obj);

    if (obj.getNumParcelas() > 1) {
      generateAllParcelas(obj);
    }
    return null;
  }

  protected void generateAllParcelas(DespesaEntity obj) {
    int parcela = 1;
    int mes = 0;

    if (obj.getNumParcelas() > 99) {
      obj.setNumParcelas(99);
    }

    obj.setIdParcela(obj.getId());
    obj = repo.save(obj);

    List<DespesaEntity> listDespesas = new ArrayList<>();
    do {
      parcela += 1;
      mes += 1;
      DespesaEntity despesa = new DespesaEntity();
      updateData(despesa, obj);
      despesa.setIdParcela(obj.getId());
      despesa.setParcelaAtual(parcela);
      despesa.setNumParcelas(obj.getNumParcelas());
      despesa.setUsuario(obj.getUsuario());
      despesa.setPago(false);
      despesa.setDtVencimento(obj.getDtVencimento().plusMonths(mes));

      listDespesas.add(despesa);
    } while (parcela < obj.getNumParcelas());

    repo.saveAll(listDespesas);
  }

  @Override
  public void updateUnpaidByIdParcela(DespesaEntity newDespesa) {
    if (isNullorZero(newDespesa.getIdParcela())) {
      throw new DataIntegrityException("IdParcela inválido");
    }

    UsuarioEntity usuario = usuarioService.userLoggedIn();
    List<DespesaEntity> despesas =
        repo.findByUsuarioAndIdParcelaAndPago(usuario, newDespesa.getIdParcela(), false);

    for (DespesaEntity despesa : despesas) {
      despesa.setDescricao(newDespesa.getDescricao());
      despesa.setValor(newDespesa.getValor());
      despesa.setCategoria(newDespesa.getCategoria());
      despesa.setConta(newDespesa.getConta());
      despesa.setDtVencimento(updateDayOfVencimento(newDespesa.getDtVencimento(), despesa.getDtVencimento()));
    }
    repo.saveAll(despesas);
  }

  protected LocalDate updateDayOfVencimento(LocalDate newDate, LocalDate oldDate) {
    try {
      return LocalDate.of(oldDate.getYear(), oldDate.getMonth(), newDate.getDayOfMonth());
    } catch (DateTimeException e) {
      return oldDate.withDayOfMonth(oldDate.lengthOfMonth());
    }
  }

  @Override
  public void updateAllByIdParcela(DespesaEntity despesa) {
    if (isNullorZero(despesa.getIdParcela())) {
      throw new DataIntegrityException("IdParcela inválido");
    }

    UsuarioEntity usuario = usuarioService.userLoggedIn();
    List<DespesaEntity> despesas = repo.findByUsuarioAndIdParcela(usuario, despesa.getIdParcela());

    for (DespesaEntity newDespesa : despesas) {
      newDespesa.setDescricao(despesa.getDescricao());
      newDespesa.setCategoria(despesa.getCategoria());
    }
    repo.saveAll(despesas);
  }

  @Override
  public void deleteByIdParcela(Integer idParcela) {

    if (isNullorZero(idParcela)) {
      throw new DataIntegrityException("IdParcela inválido");
    }

    UsuarioEntity usuario = usuarioService.userLoggedIn();
    List<DespesaEntity> despesas = repo.findByUsuarioAndIdParcelaAndPago(usuario, idParcela, false);

    try {
      repo.deleteAll(despesas);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException("Não é possivel excluir uma Despesa que possui Despesas.");
    }
  }

  @Override
  public Page<DespesaEntity> findPage(
      Integer page,
      Integer linesPerPage,
      String orderBy,
      String direction,
      String search,
      String stringDtInicial,
      String stringDtFinal,
      Boolean pago) {
    UsuarioEntity usuario = usuarioService.userLoggedIn();
    PageRequest pageRequest =
        PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

    Date dtInicial = stringToDate(stringDtInicial);
    Date dtFinal = stringToDate(stringDtFinal);

    if (pago != null) {
      return repo
          .findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqualAndPago(
              usuario, search, dtInicial, dtFinal, pageRequest, pago);
    }

    return repo
        .findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqual(
            usuario, search, dtInicial, dtFinal, pageRequest);
  }

  @Override
  public TotaisDTO totalsByPeriod(String stringDtInicial, String stringDtFinal, String search) {
    Double total = 0.0;
    Double totalPago = 0.0;
    Double totalPendente = 0.0;

    UsuarioEntity usuario = usuarioService.userLoggedIn();

    Date dtInicial = stringToDate(stringDtInicial);
    Date dtFinal = stringToDate(stringDtFinal);

    List<DespesaEntity> list =
        repo
            .findByUsuarioAndDescricaoContainingAndDtVencimentoGreaterThanEqualAndDtVencimentoLessThanEqual(
                usuario, search, dtInicial, dtFinal);

    for (DespesaEntity despesa : list) {
      total += despesa.getValor();
      if (despesa.getPago()) {
        totalPago += despesa.getValor();
      } else {
        totalPendente += despesa.getValor();
      }
    }

    return new TotaisDTO(dtInicial, dtFinal, total, totalPago, totalPendente);
  }

  @Override
  public List<TotaisByCategDTO> totalsByPeriodByCategoria(
      String stringDtInicial, String stringDtFinal, String search) {

    UsuarioEntity usuario = usuarioService.userLoggedIn();
    Date dtInicial = stringToDate(stringDtInicial);
    Date dtFinal = stringToDate(stringDtFinal);

    List<TotaisByCategDTO> list = repo.totalsByPeriodByCategoria(usuario, dtInicial, dtFinal);

    return list;
  }

  @Override
  public List<TotaisByMonthDTO> totalsByPeriodByMonth(
      String stringDtInicial, String stringDtFinal, String search) {

    UsuarioEntity usuario = usuarioService.userLoggedIn();
    Date dtInicial = stringToDate(stringDtInicial + "-01");
    Date dtFinal = stringToDate(stringDtFinal + "-" + lastDayOfMonth(stringDtFinal));

    if (monthsBetween(dtInicial, dtFinal) > 12) {
      throw new DataIntegrityException("Filtro deve ter no máximo 12 meses de intervalo");
    }

    List<TotaisByMonthDTO> list = repo.totalsByPeriodByMonth(usuario, dtInicial, dtFinal);

    try {
      fixListByMonth(list, stringDtInicial, stringDtFinal);
    } catch (Exception e) {
      throw new DataIntegrityException("Erro ao ajustar lista");
    }

    return list;
  }

  @Override
  public DespesaEntity fromDTO(DespesaDTO objDto) {
    UsuarioEntity usuario = usuarioService.userLoggedIn();
    CategoriaEntity categ = new CategoriaEntity();
    categ.setId(objDto.getCategoriaId());
    ContaEntity conta = new ContaEntity();
    if (objDto.getContaId() == null) {
      conta = null;
    } else {
      conta.setId(objDto.getContaId());
    }

    return new DespesaEntity(
        objDto.getId(),
        objDto.getDescricao(),
        objDto.getValor(),
        objDto.getDtVencimento(),
        Objects.nonNull(objDto.getPago()) && objDto.getPago(),
        !this.isNullorZero(objDto.getNumParcelas()) ? objDto.getNumParcelas() : 1,
        objDto.getParcelaAtual(),
        objDto.getIdParcela(),
        usuario,
        categ,
        conta);
  }

  @Override
  public DespesaDTO toDTO(DespesaEntity obj) {
    return new DespesaDTO(obj);
  }

  @Override
  protected void updateData(DespesaEntity newObj, DespesaEntity obj) {
    newObj.setDescricao(obj.getDescricao());
    newObj.setCategoria(obj.getCategoria());
    newObj.setConta(obj.getConta());
    newObj.setDtVencimento(obj.getDtVencimento());
    newObj.setPago(obj.getPago());
    newObj.setValor(obj.getValor());
  }

  private boolean isNullorZero(Integer i) {
    return 0 == (i == null ? 0 : i);
  }

  private Date stringToDate(String stringDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

    Date date;
    try {
      date = sdf.parse(stringDate);
    } catch (ParseException e) {
      throw new DataIntegrityException("Data inválida");
    }

    return date;
  }

  private Integer monthsBetween(Date dtInicial, Date dtFinal) {
    Calendar dtInicialCalendar = Calendar.getInstance();
    dtInicialCalendar.setTime(dtInicial);
    Calendar dtFinalCalendar = Calendar.getInstance();
    dtFinalCalendar.setTime(dtFinal);

    return (dtFinalCalendar.get(Calendar.YEAR) * 12 + dtFinalCalendar.get(Calendar.MONTH))
        - (dtInicialCalendar.get(Calendar.YEAR) * 12 + dtInicialCalendar.get(Calendar.MONTH));
  }

  private Integer lastDayOfMonth(String month) {
    Date data = stringToDate(month + "-01");
    Calendar cal = Calendar.getInstance();
    cal.setTime(data);
    return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  private void fixListByMonth(
      List<TotaisByMonthDTO> list, String stringDtInicial, String stringDtFinal) throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    Calendar dtInicialCalendar = Calendar.getInstance();
    Calendar dtFinalCalendar = Calendar.getInstance();

    dtInicialCalendar.setTime(sdf.parse(stringDtInicial));
    dtFinalCalendar.setTime(sdf.parse(stringDtFinal));
    dtFinalCalendar.add(Calendar.MONTH, 1);

    while (dtInicialCalendar.before(dtFinalCalendar)) {
      TotaisByMonthDTO mes =
          new TotaisByMonthDTO(
              dtInicialCalendar.get(Calendar.MONTH) + 1, dtInicialCalendar.get(Calendar.YEAR), 0.0);
      if (!list.contains(mes)) {
        list.add(mes);
      }
      dtInicialCalendar.add(Calendar.MONTH, 1);
    }

    Collections.sort(
        list,
        new Comparator<TotaisByMonthDTO>() {
          @Override
          public int compare(TotaisByMonthDTO b, TotaisByMonthDTO a) {
            int res = a.getAno().compareTo(b.getAno());
            if (res != 0) {
              return res;
            }
            return a.getMes().compareTo(b.getMes());
          }
        });
  }
}
