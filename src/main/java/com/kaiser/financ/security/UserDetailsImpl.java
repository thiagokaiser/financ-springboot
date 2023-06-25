package com.kaiser.financ.security;

import com.kaiser.financ.entities.enums.PerfilEnum;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Integer id;
  private String email;
  private String senha;
  private String nome;
  private String sobrenome;
  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl() {}

  public UserDetailsImpl(
      Integer id, String email, String senha, Set<PerfilEnum> perfis, String nome, String sobrenome) {
    super();
    this.id = id;
    this.email = email;
    this.senha = senha;
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.authorities =
        perfis.stream()
            .map(x -> new SimpleGrantedAuthority(x.getDescricao()))
            .collect(Collectors.toList());
  }

  public Integer getId() {
    return id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return email;
  }

  public String getNome() {
    return nome;
  }

  public String getSobrenome() {
    return sobrenome;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public boolean hasRole(PerfilEnum perfil) {
    return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
  }
}
