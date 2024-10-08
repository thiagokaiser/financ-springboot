package com.kaiser.financ.dtos;

import static java.util.Objects.nonNull;

import com.kaiser.financ.entities.CategoriaEntity;
import com.kaiser.financ.entities.ContaEntity;
import com.kaiser.financ.entities.DespesaEntity;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DespesaDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer id;

  @NotEmpty(message = "Campo Obrigatário")
  @Size(min = 3, max = 80, message = "Campo deve estar entre 3 e 80 caracteres")
  private String descricao;

  private Double valor;
  private LocalDate dtVencimento;
  private Boolean pago;
  private LocalDate dtPagamento;
  private Integer numParcelas;
  private Integer parcelaAtual;
  private Integer idParcela;
  private Integer categoriaId;
  private CategoriaEntity categoria;
  private Integer contaId;
  private ContaEntity conta;

  public DespesaDTO() {}

  public DespesaDTO(DespesaEntity obj) {
    this.id = obj.getId();
    this.descricao = obj.getDescricao();
    this.valor = obj.getValor();
    this.dtVencimento = obj.getDtVencimento();
    this.pago = obj.getPago();
    this.dtPagamento = obj.getDtPagamento();
    this.numParcelas = obj.getNumParcelas();
    this.parcelaAtual = obj.getParcelaAtual();
    this.idParcela = obj.getIdParcela();
    this.categoria = obj.getCategoria();
    this.categoriaId = nonNull(obj.getCategoria()) ? obj.getCategoria().getId() : null;
    this.conta = obj.getConta();
    this.contaId = nonNull(obj.getConta()) ? obj.getConta().getId() : null;
  }
  
}
