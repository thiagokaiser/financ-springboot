package com.kaiser.financ.services.impl;

import com.kaiser.financ.repositories.UsuarioRepository;
import com.kaiser.financ.services.CategoriaService;
import com.kaiser.financ.services.ContaService;
import com.kaiser.financ.services.DeleteUsuarioService;
import com.kaiser.financ.services.DespesaService;
import com.kaiser.financ.services.UsuarioService;
import com.kaiser.financ.services.exceptions.DataIntegrityException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUsuarioServiceImpl implements DeleteUsuarioService {

  private final UsuarioService usuarioService;
  private final CategoriaService categoriaService;
  private final ContaService contaService;
  private final DespesaService despesaService;
  private final UsuarioRepository repository;

  @Override
  public void delete(Integer id) {
    usuarioService.find(id);
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException("Não é possivel excluir um cliente que possui Pedidos.");
    }
  }

  @Override
  public void deleteUserData(Integer id) {
    try {
      despesaService.deleteByUsuario(id);
      categoriaService.deleteByUsuario(id);
      contaService.deleteByUsuario(id);
    } catch (Exception e) {
      throw new DataIntegrityException("Não é possivel excluir.");
    }
  }

}
