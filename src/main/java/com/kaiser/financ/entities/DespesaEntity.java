package com.kaiser.financ.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Despesa")
public class DespesaEntity extends BaseEntity {
  @NotBlank(message = "Campo Obrigatário")
  private String descricao;

  private Double valor;

  @NotNull(message = "Campo Obrigatário")
  private Date dtVencimento;

  private Boolean pago;

  private Integer numParcelas;

  private Integer parcelaAtual;

  private Integer idParcela;

  @ManyToOne
  @JoinColumn(name = "categoria_id")
  private CategoriaEntity categoria;

  @ManyToOne(optional = true)
  @JoinColumn(name = "conta_id")
  private ContaEntity conta;

  public DespesaEntity() {}

  public DespesaEntity(
      Integer id,
      String descricao,
      Double valor,
      Date dtVencimento,
      Boolean pago,
      Integer numParcelas,
      Integer parcelaAtual,
      Integer idParcela,
      UsuarioEntity usuario,
      CategoriaEntity categoria,
      ContaEntity conta) {
    super(id, usuario);
    this.descricao = descricao;
    this.valor = valor;
    this.dtVencimento = dtVencimento;
    this.pago = pago;
    this.numParcelas = numParcelas;
    this.parcelaAtual = parcelaAtual;
    this.idParcela = idParcela;
    this.categoria = categoria;
    this.conta = conta;
  }
  
}
