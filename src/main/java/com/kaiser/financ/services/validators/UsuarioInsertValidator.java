package com.kaiser.financ.services.validators;

import com.kaiser.financ.controllers.exceptions.FieldMessage;
import com.kaiser.financ.dtos.UsuarioNewDTO;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.repositories.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, UsuarioNewDTO> {

  @Autowired private
  UsuarioRepository repo;

  @Override
  public void initialize(UsuarioInsert ann) {}

  @Override
  public boolean isValid(UsuarioNewDTO objDto, ConstraintValidatorContext context) {

    List<FieldMessage> list = new ArrayList<>();

    UsuarioEntity aux = repo.findByEmail(objDto.getEmail());
    if (aux != null) {
      list.add(new FieldMessage("email", "Email já cadastrado"));
    }

    for (FieldMessage e : list) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(e.getMessage())
          .addPropertyNode(e.getFieldName())
          .addConstraintViolation();
    }
    return list.isEmpty();
  }
}
