package com.kaiser.financ.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaiser.financ.domain.Despesa;
import com.kaiser.financ.dto.DespesaDTO;
import com.kaiser.financ.dto.DespesaUpdateDTO;
import com.kaiser.financ.dto.TotaisByCategDTO;
import com.kaiser.financ.dto.TotaisByMonthDTO;
import com.kaiser.financ.dto.TotaisDTO;
import com.kaiser.financ.services.DespesaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/despesas")
public class DespesaResource {

	@Autowired
	private DespesaService service;	
	
	@ApiOperation(value = "Busca por id")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Despesa> find(@PathVariable Integer id) {
		
		Despesa obj = service.find(id);		
		return ResponseEntity.ok().body(obj);		
		
	}
	
	@ApiOperation(value = "Insere Despesa")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody DespesaUpdateDTO objDto){		
		
		Despesa obj = service.fromDTO(objDto);
		service.insert(obj);		
		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Atualiza Despesa")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody DespesaUpdateDTO objDto, @PathVariable Integer id){
		Despesa obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Atualiza Despesa todas despesas pelo identificador")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value = "/all/{identificador}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateAllByIdentificador(@Valid @RequestBody DespesaUpdateDTO objDto, @PathVariable Integer identificador){
		Despesa obj = service.fromDTO(objDto);
		obj.setIdentificador(identificador);
		service.updateAllByIdentificador(obj);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Atualiza Despesa todas despesas nao pagas pelo identificador")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value = "/unpaid/{identificador}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUnpaidByIdentificador(@Valid @RequestBody DespesaUpdateDTO objDto, @PathVariable Integer identificador){
		Despesa obj = service.fromDTO(objDto);
		obj.setIdentificador(identificador);
		service.updateUnpaidByIdentificador(obj);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Remove Despesa")
	@ApiResponses(value = {			
			@ApiResponse(code = 404, message = "Código inexistente") })
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		service.delete(id);		
		return ResponseEntity.noContent().build();		
		
	}
	
	@ApiOperation(value = "Remove Despesa pelo identificador (despesa parcelada)")
	@ApiResponses(value = {			
			@ApiResponse(code = 404, message = "Código inexistente") })
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value="/identificador/{identificador}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteByIdentificador(@PathVariable Integer identificador) {
		
		service.deleteByIdentificador(identificador);		
		return ResponseEntity.noContent().build();		
		
	}
	
	@ApiOperation(value = "Retorna todas Despesas")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<DespesaDTO>> findAll() {
		
		List<Despesa> list = service.findAll();
		List<DespesaDTO> listDto = list.stream().map(obj -> new DespesaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);		
		
	}
	
	@ApiOperation(value = "Retorna todas Despesas com paginação")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<DespesaDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "descricao") String orderBy, 
			@RequestParam(value="direction", defaultValue = "DESC") String direction,
			@RequestParam(value="search") String search,
			@RequestParam(value="dtInicial") String dtInicial,
			@RequestParam(value="dtFinal") String dtFinal,
			@RequestParam(value="pago", required=false) Boolean pago) {		
		
		Page<Despesa> list = service.findPage(page, linesPerPage, orderBy, direction, search, dtInicial, dtFinal, pago);
		Page<DespesaDTO> listDto = list.map(obj -> new DespesaDTO(obj));
		return ResponseEntity.ok().body(listDto);				
		
	}	
	
	@ApiOperation(value = "Retorna Totais por periodo")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value="/totals", method=RequestMethod.GET)
	public ResponseEntity<TotaisDTO> totalsByPeriod(
			@RequestParam(value="search") String search,
			@RequestParam(value="dtInicial") String dtInicial,
			@RequestParam(value="dtFinal") String dtFinal) {		
		
		TotaisDTO totais = service.totalsByPeriod(dtInicial, dtFinal, search);		
		return ResponseEntity.ok().body(totais);		
	}
	
	@ApiOperation(value = "Retorna Totais por Categoria")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value="/totalsByCateg", method=RequestMethod.GET)
	public ResponseEntity<List<TotaisByCategDTO>> totalsByPeriodByCategoria(
			@RequestParam(value="search") String search,
			@RequestParam(value="dtInicial") String dtInicial,
			@RequestParam(value="dtFinal") String dtFinal) {		
		
		List<TotaisByCategDTO> totais = service.totalsByPeriodByCategoria(dtInicial, dtFinal, search);		
		return ResponseEntity.ok().body(totais);		
	}
	
	@ApiOperation(value = "Retorna Totais por Mes")
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value="/totalsByMonth", method=RequestMethod.GET)
	public ResponseEntity<List<TotaisByMonthDTO>> totalsByPeriodByMonth(
			@RequestParam(value="search") String search,
			@RequestParam(value="dtInicial") String dtInicial,
			@RequestParam(value="dtFinal") String dtFinal) {		
		
		List<TotaisByMonthDTO> totais = service.totalsByPeriodByMonth(dtInicial, dtFinal, search);		
		return ResponseEntity.ok().body(totais);		
	}
}
