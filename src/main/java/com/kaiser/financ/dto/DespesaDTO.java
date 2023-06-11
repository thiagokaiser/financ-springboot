package com.kaiser.financ.dto;

import static java.util.Objects.nonNull;

import com.kaiser.financ.domain.Categoria;
import com.kaiser.financ.domain.Conta;
import com.kaiser.financ.domain.Despesa;
import java.io.Serializable;
import java.util.Date;
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
  private Date dtVencimento;
  private Boolean pago;
  private Integer numParcelas;
  private Integer parcelaAtual;
  private Integer idParcela;
  private Integer categoriaId;
  private Categoria categoria;
  private Integer contaId;
  private Conta conta;

  public DespesaDTO() {}

  public DespesaDTO(Despesa obj) {
    this.id = obj.getId();
    this.descricao = obj.getDescricao();
    this.valor = obj.getValor();
    this.dtVencimento = obj.getDtVencimento();
    this.pago = obj.getPago();
    this.numParcelas = obj.getNumParcelas();
    this.parcelaAtual = obj.getParcelaAtual();
    this.idParcela = obj.getIdParcela();
    this.categoria = obj.getCategoria();
    this.categoriaId = nonNull(obj.getCategoria()) ? obj.getCategoria().getId() : null;
    this.conta = obj.getConta();
    this.contaId = nonNull(obj.getConta()) ? obj.getConta().getId() : null;
  }
  
}
