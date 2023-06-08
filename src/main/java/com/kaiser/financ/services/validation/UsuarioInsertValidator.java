package com.kaiser.financ.services.validation;

import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.dto.UsuarioNewDTO;
import com.kaiser.financ.repositories.UsuarioRepository;
import com.kaiser.financ.resources.exceptions.FieldMessage;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, UsuarioNewDTO> {
	
	@Autowired
	private UsuarioRepository repo;
	
	@Override
	public void initialize(UsuarioInsert ann) {
	}

	@Override
	public boolean isValid(UsuarioNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();		
		
		Usuario aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já cadastrado"));			
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}