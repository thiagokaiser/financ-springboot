package com.kaiser.financ.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaiser.financ.dtos.UsuarioUpdateAdminDTO;
import com.kaiser.financ.dtos.UsuarioUpdateDTO;
import com.kaiser.financ.entities.enums.PerfilEnum;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Usuario")
public class UsuarioEntity implements Serializable {
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
  private Date lastLogin;

  @Column(unique = true)
  private String email;

  @JsonIgnore private String senha;

  @ElementCollection
  @CollectionTable(name = "TELEFONE")
  private Set<String> telefones = new HashSet<>();

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PERFIS")
  private final Set<Integer> perfis = new HashSet<>();

  public UsuarioEntity() {}

  public UsuarioEntity(Integer id, String nome, String sobrenome, String email, String senha) {
    super();
    this.id = id;
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.email = email;
    this.senha = senha;
  }

  public UsuarioEntity(UsuarioUpdateDTO objDto) {
    super();
    this.nome = objDto.getNome();
    this.sobrenome = objDto.getSobrenome();
    this.dtNascimento = objDto.getDtNascimento();
    this.cidade = objDto.getCidade();
    this.estado = objDto.getEstado();
    this.descricao = objDto.getDescricao();
  }

  public UsuarioEntity(UsuarioUpdateAdminDTO objDto) {
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

  public Set<PerfilEnum> getPerfis() {
    return perfis.stream().map(x -> PerfilEnum.toEnum(x)).collect(Collectors.toSet());
  }

  public void addPerfil(PerfilEnum perfil) {
    perfis.add(perfil.getCod());
  }

  public void removePerfil(PerfilEnum perfil) {
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
    UsuarioEntity other = (UsuarioEntity) obj;
    if (id == null) {
      return other.id == null;
    } else return id.equals(other.id);
  }
}
