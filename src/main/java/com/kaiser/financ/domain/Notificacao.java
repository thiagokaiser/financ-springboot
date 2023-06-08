package com.kaiser.financ.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Notificacao implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Campo Obrigatário")
    private String descricao;
        
    private Date dtCriacao;
    
    private Date dtLeitura;
    
    @ManyToOne
    @JoinColumn(name="usuario_id")
    @NotNull(message = "Campo Obrigatário")
    private Usuario usuario;
    
    private boolean lido;

    public Notificacao() {
    }
    
    public Notificacao(Integer id, String descricao, Date dtCriacao, Date dtLeitura, Usuario usuario, boolean lido) {    
        this.id = id;
        this.descricao = descricao;
        this.dtCriacao = dtCriacao;
        this.dtLeitura = dtLeitura;
        this.usuario = usuario;
        this.lido = lido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(Date dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public Date getDtLeitura() {
        return dtLeitura;
    }

    public void setDtLeitura(Date dtLeitura) {
        this.dtLeitura = dtLeitura;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLido() {
        return lido;
    }

    public void setLido(boolean lido) {
        this.lido = lido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Notificacao other = (Notificacao) obj;
        return Objects.equals(id, other.id);
    }

}
