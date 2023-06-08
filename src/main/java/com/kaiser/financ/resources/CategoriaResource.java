package com.kaiser.financ.resources;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.dto.CategoriaDTO;
import com.kaiser.financ.services.CategoriaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/categorias") 
public class CategoriaResource extends CrudResource<CategoriaService, Categoria, CategoriaDTO>{

  @ApiOperation(value = "Find all paginated")
  @GetMapping(value="/page")
  public ResponseEntity<Page<CategoriaDTO>> findPage(
      @RequestParam(value="page", defaultValue = "0") Integer page,
      @RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
      @RequestParam(value="orderBy", defaultValue = "descricao") String orderBy,
      @RequestParam(value="direction", defaultValue = "DESC") String direction,
      @RequestParam(value="search") String search) {

    Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction, search);
    Page<CategoriaDTO> listDto = list.map(service::toDTO);
    return ResponseEntity.ok().body(listDto);

  }

}
