package com.kaiser.financ.dto;

import com.kaiser.financ.domain.Notificacao;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class NotificacaoDTO implements Serializable{
    private static final long serialVersionUID = 1L;
        
    private Integer id;       
    private String descricao;        
    private Date dtCriacao;    
    private Date dtLeitura;
    private UsuarioDTO usuario;
    private boolean lido;

    public NotificacaoDTO() {
    }
    
    public NotificacaoDTO(Notificacao notificacao) {
        this.id = notificacao.getId();
        this.descricao = notificacao.getDescricao();
        this.dtCriacao = notificacao.getDtCriacao();
        this.dtLeitura = notificacao.getDtLeitura();        
        this.lido = notificacao.isLido();
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

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
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
		NotificacaoDTO other = (NotificacaoDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}
