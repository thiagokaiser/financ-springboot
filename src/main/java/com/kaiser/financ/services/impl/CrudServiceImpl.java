package com.kaiser.financ.services.impl;

import com.kaiser.financ.entities.BaseEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.repositories.CrudRepository;
import com.kaiser.financ.services.CrudService;
import com.kaiser.financ.services.UsuarioService;
import com.kaiser.financ.services.exceptions.DataIntegrityException;
import com.kaiser.financ.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public abstract class CrudServiceImpl<D extends BaseEntity, R extends CrudRepository, T>
    implements CrudService<D, T> {

  @Autowired
  protected R repo;
  @Autowired
  protected UsuarioService usuarioService;

  @Override
  public D find(Integer id) {
    UsuarioEntity usuario = usuarioService.userLoggedIn();
    Optional<D> obj = (Optional<D>) repo.findByIdAndUsuario(id, usuario);
    return obj.orElseThrow(
        () ->
            new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: ")); // TODO tipo
  }

  @Override
  public D insert(D obj) {
    obj.setId(null);
    return (D) repo.save(obj);
  }

  @Override
  public D update(D obj) {
    D newObj = find(obj.getId());
    updateData(newObj, obj);
    return (D) repo.save(newObj);
  }

  @Override
  public void delete(Integer id) {
    find(id);
    try {
      repo.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException("Não é possivel excluir.");
    }
  }

  @Override
  public List<D> findAll() {
    UsuarioEntity usuario = usuarioService.userLoggedIn();
    return (List<D>) repo.findByUsuario(usuario);
  }

  protected abstract void updateData(D newObj, D obj);
}
