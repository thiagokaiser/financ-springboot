package com.kaiser.financ.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kaiser.financ.dtos.DespesaDTO;
import com.kaiser.financ.entities.CategoriaEntity;
import com.kaiser.financ.entities.ContaEntity;
import com.kaiser.financ.entities.DespesaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.repositories.DespesaRepository;
import com.kaiser.financ.services.CategoriaService;
import com.kaiser.financ.services.ContaService;
import com.kaiser.financ.services.UsuarioService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DespesaServiceImplTest {

  @InjectMocks
  private DespesaServiceImpl despesaService;

  @Mock
  private DespesaRepository despesaRepository;

  @Mock
  private CategoriaService categoriaService;

  @Mock
  private ContaService contaService;

  @Mock
  private UsuarioService usuarioService;

  private DespesaEntity despesaEntity;
  private CategoriaEntity categoriaEntity;
  private ContaEntity contaEntity;
  private UsuarioEntity usuarioEntity;

  @BeforeEach
  void setUp() {
    categoriaEntity = new CategoriaEntity();
    categoriaEntity.setId(1);

    contaEntity = new ContaEntity();
    contaEntity.setId(1);

    usuarioEntity = new UsuarioEntity();
    usuarioEntity.setId(1);

    despesaEntity = new DespesaEntity();
    despesaEntity.setId(1);
    despesaEntity.setDescricao("Despesa Teste");
    despesaEntity.setValor(100.0);
    despesaEntity.setDtVencimento(LocalDate.of(2024, 8, 15));
    despesaEntity.setPago(false);
    despesaEntity.setNumParcelas(3);
    despesaEntity.setParcelaAtual(1);
    despesaEntity.setIdParcela(1);
    despesaEntity.setCategoria(categoriaEntity);
    despesaEntity.setConta(contaEntity);
    despesaEntity.setUsuario(usuarioEntity);
  }

  @Test
  void testInsert() {
    when(categoriaService.find(1)).thenReturn(categoriaEntity);
    when(contaService.find(1)).thenReturn(contaEntity);

    when(despesaRepository.save(any(DespesaEntity.class))).thenReturn(despesaEntity);

    DespesaEntity result = despesaService.insert(despesaEntity);

    verify(despesaRepository, times(2)).save(despesaEntity);
    assertNull(result);
  }

  @Test
  void testGenerateAllParcelas() {
    when(despesaRepository.save(any(DespesaEntity.class))).thenReturn(despesaEntity);

    despesaService.generateAllParcelas(despesaEntity);

    verify(despesaRepository, times(1)).save(any(DespesaEntity.class));
  }

  @Test
  void testUpdateUnpaidByIdParcela() {
    List<DespesaEntity> despesas = new ArrayList<>();
    despesas.add(despesaEntity);

    when(despesaRepository.findByUsuarioAndIdParcelaAndPago(any(), anyInt(), anyBoolean())).thenReturn(despesas);
    when(despesaRepository.saveAll(anyList())).thenReturn(despesas);

    despesaService.updateUnpaidByIdParcela(despesaEntity);

    verify(despesaRepository, times(1)).saveAll(anyList());
  }

  @Test
  void testUpdateDayOfVencimento() {
    LocalDate newDtVencimento = LocalDate.of(2024, 8, 15);
    LocalDate oldDtVencimento = LocalDate.of(2024, 2, 29);

    LocalDate updatedDate = despesaService.updateDayOfVencimento(newDtVencimento, oldDtVencimento);

    assertEquals(LocalDate.of(2024, 2, 15), updatedDate);
  }

  @Test
  void testUpdateDayOfVencimentoInvalidDate() {
    LocalDate newDtVencimento = LocalDate.of(2024, 8, 31);
    LocalDate oldDtVencimento = LocalDate.of(2024, 2, 10);

    LocalDate updatedDate = despesaService.updateDayOfVencimento(newDtVencimento, oldDtVencimento);

    assertEquals(LocalDate.of(2024, 2, 29), updatedDate);
  }

  @Test
  void testUpdateAllByIdParcela() {
    List<DespesaEntity> despesas = new ArrayList<>();
    despesas.add(despesaEntity);

    when(despesaRepository.findByUsuarioAndIdParcela(any(), anyInt())).thenReturn(despesas);
    when(despesaRepository.saveAll(anyList())).thenReturn(despesas);

    despesaService.updateAllByIdParcela(despesaEntity);

    verify(despesaRepository, times(1)).saveAll(anyList());
  }

  @Test
  void testDeleteByIdParcela() {
    List<DespesaEntity> despesas = new ArrayList<>();
    despesas.add(despesaEntity);

    when(despesaRepository.findByUsuarioAndIdParcelaAndPago(any(), anyInt(), anyBoolean())).thenReturn(despesas);
    doNothing().when(despesaRepository).deleteAll(anyList());

    despesaService.deleteByIdParcela(1);

    verify(despesaRepository, times(1)).deleteAll(anyList());
  }

  @Test
  void testFromDTO() {
    DespesaDTO dto = new DespesaDTO();
    dto.setId(1);
    dto.setDescricao("Despesa DTO");
    dto.setValor(200.0);
    dto.setDtVencimento(LocalDate.of(2024, 9, 15));
    dto.setPago(false);
    dto.setNumParcelas(2);
    dto.setParcelaAtual(1);
    dto.setIdParcela(1);
    dto.setCategoriaId(1);
    dto.setContaId(1);

    when(usuarioService.userLoggedIn()).thenReturn(usuarioEntity);

    DespesaEntity result = despesaService.fromDTO(dto);

    assertNotNull(result);
    assertEquals(dto.getDescricao(), result.getDescricao());
    assertEquals(dto.getValor(), result.getValor());
    assertEquals(dto.getDtVencimento(), result.getDtVencimento());
    assertEquals(dto.getNumParcelas(), result.getNumParcelas());
    assertEquals(dto.getParcelaAtual(), result.getParcelaAtual());
    assertEquals(dto.getIdParcela(), result.getIdParcela());
    assertEquals(usuarioEntity, result.getUsuario());
    assertEquals(categoriaEntity, result.getCategoria());
    assertEquals(contaEntity, result.getConta());
  }

  @Test
  void testToDTO() {
    DespesaDTO dto = despesaService.toDTO(despesaEntity);

    assertNotNull(dto);
    assertEquals(despesaEntity.getDescricao(), dto.getDescricao());
    assertEquals(despesaEntity.getValor(), dto.getValor());
    assertEquals(despesaEntity.getDtVencimento(), dto.getDtVencimento());
    assertEquals(despesaEntity.getNumParcelas(), dto.getNumParcelas());
    assertEquals(despesaEntity.getParcelaAtual(), dto.getParcelaAtual());
    assertEquals(despesaEntity.getIdParcela(), dto.getIdParcela());
  }
}
