package com.kaiser.financ.services.impl;

import com.kaiser.financ.entities.CategoriaEntity;
import com.kaiser.financ.entities.ContaEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.entities.enums.PerfilEnum;
import com.kaiser.financ.repositories.CategoriaRepository;
import com.kaiser.financ.repositories.ContaRepository;
import com.kaiser.financ.repositories.UsuarioRepository;
import com.kaiser.financ.services.DBService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBServiceImpl implements DBService {

  @Autowired
  private BCryptPasswordEncoder pe;

  @Autowired
  private CategoriaRepository categoriaRepo;

  @Autowired
  private ContaRepository contaRepo;

  @Autowired
  private UsuarioRepository usuarioRepo;

  @Override
  public void instantiateTestDatabase() {

    UsuarioEntity user = new UsuarioEntity(null, "user", "sobrenome", "user@teste.com", pe.encode("123"));
    user.addPerfil(PerfilEnum.USER);
    UsuarioEntity user2 = new UsuarioEntity(null, "admin", "sobrenome", "admin@teste.com", pe.encode("123"));
    user2.addPerfil(PerfilEnum.ADMIN);
    user2.addPerfil(PerfilEnum.USER);
    usuarioRepo.saveAll(Arrays.asList(user, user2));

    CategoriaEntity cat1 = new CategoriaEntity(null, "Categ 01", "#5eaeff", user);
    CategoriaEntity cat2 = new CategoriaEntity(null, "Categ 02", "#ff1a1f", user);
    CategoriaEntity cat3 = new CategoriaEntity(null, "Categ 03", "#84ff84", user2);
    CategoriaEntity cat4 = new CategoriaEntity(null, "Categ 04", "#ef87f8", user2);

    categoriaRepo.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));

    ContaEntity conta1 = new ContaEntity(null, "Conta 01", user);
    ContaEntity conta2 = new ContaEntity(null, "Conta 02", user);
    ContaEntity conta3 = new ContaEntity(null, "Conta 03", user2);
    ContaEntity conta4 = new ContaEntity(null, "Conta 04", user2);

    contaRepo.saveAll(Arrays.asList(conta1, conta2, conta3, conta4));
  }
}
