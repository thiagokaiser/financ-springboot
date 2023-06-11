package com.kaiser.financ.dto;

import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.domain.enums.Perfil;
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
  private Set<Perfil> perfis = new HashSet<>();

  public UsuarioDTO(Usuario obj) {
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
  }

}
