package com.kaiser.financ.controllers;

import com.kaiser.financ.dtos.DespesaDTO;
import com.kaiser.financ.dtos.TotaisByCategDTO;
import com.kaiser.financ.dtos.TotaisByMonthDTO;
import com.kaiser.financ.dtos.TotaisDTO;
import com.kaiser.financ.entities.DespesaEntity;
import com.kaiser.financ.services.DespesaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/despesas")
public class DespesaController extends CrudController<DespesaService, DespesaEntity, DespesaDTO> {

  @PostMapping
  @Override
  public ResponseEntity<Void> insert(@Valid @RequestBody DespesaDTO objDto) {

    DespesaEntity obj = service.fromDTO(objDto);
    service.insert(obj);

    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/all/{idParcela}")
  public ResponseEntity<Void> updateAllByIdParcela(
      @Valid @RequestBody DespesaDTO objDto, @PathVariable Integer idParcela) {
    DespesaEntity obj = service.fromDTO(objDto);
    obj.setIdParcela(idParcela);
    service.updateAllByIdParcela(obj);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/unpaid/{idParcela}")
  public ResponseEntity<Void> updateUnpaidByIdParcela(
      @Valid @RequestBody DespesaDTO objDto, @PathVariable Integer idParcela) {
    DespesaEntity obj = service.fromDTO(objDto);
    obj.setIdParcela(idParcela);
    service.updateUnpaidByIdParcela(obj);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(value = "/idParcela/{idParcela}")
  public ResponseEntity<Void> deleteByIdParcela(@PathVariable Integer idParcela) {

    service.deleteByIdParcela(idParcela);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/page")
  public ResponseEntity<Page<DespesaDTO>> findPage(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
      @RequestParam(value = "orderBy", defaultValue = "descricao") String orderBy,
      @RequestParam(value = "direction", defaultValue = "DESC") String direction,
      @RequestParam(value = "search") String search,
      @RequestParam(value = "dtInicial") String dtInicial,
      @RequestParam(value = "dtFinal") String dtFinal,
      @RequestParam(value = "pago", required = false) Boolean pago) {

    Page<DespesaEntity> list =
        service.findPage(page, linesPerPage, orderBy, direction, search, dtInicial, dtFinal, pago);
    Page<DespesaDTO> listDto = list.map(DespesaDTO::new);
    return ResponseEntity.ok().body(listDto);
  }

  @GetMapping(value = "/totals")
  public ResponseEntity<TotaisDTO> totalsByPeriod(
      @RequestParam(value = "search") String search,
      @RequestParam(value = "dtInicial") String dtInicial,
      @RequestParam(value = "dtFinal") String dtFinal) {

    TotaisDTO totais = service.totalsByPeriod(dtInicial, dtFinal, search);
    return ResponseEntity.ok().body(totais);
  }

  @GetMapping(value = "/totalsByCateg")
  public ResponseEntity<List<TotaisByCategDTO>> totalsByPeriodByCategoria(
      @RequestParam(value = "search") String search,
      @RequestParam(value = "dtInicial") String dtInicial,
      @RequestParam(value = "dtFinal") String dtFinal) {

    List<TotaisByCategDTO> totais = service.totalsByPeriodByCategoria(dtInicial, dtFinal, search);
    return ResponseEntity.ok().body(totais);
  }

  @GetMapping(value = "/totalsByMonth")
  public ResponseEntity<List<TotaisByMonthDTO>> totalsByPeriodByMonth(
      @RequestParam(value = "search") String search,
      @RequestParam(value = "dtInicial") String dtInicial,
      @RequestParam(value = "dtFinal") String dtFinal) {

    List<TotaisByMonthDTO> totais = service.totalsByPeriodByMonth(dtInicial, dtFinal, search);
    return ResponseEntity.ok().body(totais);
  }
}
