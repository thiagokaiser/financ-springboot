package com.kaiser.financ.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaiser.financ.domain.enums.Perfil;
import com.kaiser.financ.dto.UsuarioUpdateAdminDTO;
import com.kaiser.financ.dto.UsuarioUpdateDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nome;
  private String sobrenome;
  private Date dtNascimento;
  private String cidade;
  private String estado;
  private String descricao;
  private String imagemPerfil;

  @Column(unique = true)
  private String email;

  @JsonIgnore private String senha;

  @ElementCollection
  @CollectionTable(name = "TELEFONE")
  private Set<String> telefones = new HashSet<>();

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PERFIS")
  private final Set<Integer> perfis = new HashSet<>();

  public Usuario() {}

  public Usuario(Integer id, String nome, String sobrenome, String email, String senha) {
    super();
    this.id = id;
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.email = email;
    this.senha = senha;
  }

  public Usuario(UsuarioUpdateDTO objDto) {
    super();
    this.nome = objDto.getNome();
    this.sobrenome = objDto.getSobrenome();
    this.dtNascimento = objDto.getDtNascimento();
    this.cidade = objDto.getCidade();
    this.estado = objDto.getEstado();
    this.descricao = objDto.getDescricao();
  }

  public Usuario(UsuarioUpdateAdminDTO objDto) {
    super();
    this.email = objDto.getEmail();
    this.nome = objDto.getNome();
    this.sobrenome = objDto.getSobrenome();
    this.dtNascimento = objDto.getDtNascimento();
    this.cidade = objDto.getCidade();
    this.estado = objDto.getEstado();
    this.descricao = objDto.getDescricao();
    this.imagemPerfil = objDto.getImagemPerfil();
  }

  public Set<Perfil> getPerfis() {
    return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
  }

  public void addPerfil(Perfil perfil) {
    perfis.add(perfil.getCod());
  }

  public void removePerfil(Perfil perfil) {
    perfis.remove(perfil.getCod());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Usuario other = (Usuario) obj;
    if (id == null) {
      return other.id == null;
    } else return id.equals(other.id);
  }
}
