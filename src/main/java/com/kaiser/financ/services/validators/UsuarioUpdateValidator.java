package com.kaiser.financ.services.validators;

import com.kaiser.financ.controllers.exceptions.FieldMessage;
import com.kaiser.financ.dtos.UsuarioUpdateAdminDTO;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.repositories.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

public class UsuarioUpdateValidator
    implements ConstraintValidator<UsuarioUpdate, UsuarioUpdateAdminDTO> {

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private UsuarioRepository repo;

  @Override
  public void initialize(UsuarioUpdate ann) {}

  @Override
  public boolean isValid(UsuarioUpdateAdminDTO objDto, ConstraintValidatorContext context) {

    @SuppressWarnings("unchecked")
    Map<String, String> map =
        (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    String uriEmail = map.get("email");

    List<FieldMessage> list = new ArrayList<>();

    UsuarioEntity aux = repo.findByEmail(objDto.getEmail());
    if (aux != null && !aux.getEmail().equals(uriEmail)) {
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
