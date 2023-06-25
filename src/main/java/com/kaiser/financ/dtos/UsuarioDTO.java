package com.kaiser.financ.dtos;

import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.entities.enums.PerfilEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer id;
  private String nome;
  private String sobrenome;
  private Date dtNascimento;
  private String cidade;
  private String estado;
  private String descricao;
  private String imagemPerfil;
  private String email;
  private Set<PerfilEnum> perfis = new HashSet<>();
  private Date lastLogin;

  public UsuarioDTO(UsuarioEntity obj) {
    this.id = obj.getId();
    this.nome = obj.getNome();
    this.sobrenome = obj.getSobrenome();
    this.dtNascimento = obj.getDtNascimento();
    this.cidade = obj.getCidade();
    this.estado = obj.getEstado();
    this.descricao = obj.getDescricao();
    this.imagemPerfil = obj.getImagemPerfil();
    this.email = obj.getEmail();
    this.perfis = obj.getPerfis();
    this.lastLogin = obj.getLastLogin();
  }

}
