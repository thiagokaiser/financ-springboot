package com.kaiser.financ.resources;

import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.dto.ContaDTO;
import com.kaiser.financ.services.ContaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/contas")
public class ContaResource extends CrudResource<ContaService, Conta, ContaDTO> {

  @ApiOperation(value = "Find all paginated")
  @GetMapping(value = "/page")
  public ResponseEntity<Page<ContaDTO>> findPage(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
      @RequestParam(value = "orderBy", defaultValue = "descricao") String orderBy,
      @RequestParam(value = "direction", defaultValue = "DESC") String direction,
      @RequestParam(value = "search") String search) {

    Page<Conta> list = service.findPage(page, linesPerPage, orderBy, direction, search);
    Page<ContaDTO> listDto = list.map(service::toDTO);
    return ResponseEntity.ok().body(listDto);
  }
}
