package com.kaiser.financ.services;

import com.kaiser.financ.dtos.UsuarioDTO;
import com.kaiser.financ.dtos.UsuarioNewDTO;
import com.kaiser.financ.dtos.UsuarioUpdateAdminDTO;
import com.kaiser.financ.dtos.UsuarioUpdateDTO;
import com.kaiser.financ.entities.UsuarioEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioService {

  UsuarioEntity userLoggedIn();

  UsuarioEntity find(Integer id);

  UsuarioEntity insert(UsuarioEntity obj);

  UsuarioEntity update(UsuarioEntity obj);

  UsuarioEntity updateAdmin(UsuarioEntity obj);

  void delete(Integer id);

  void removePerfil(Integer usuarioId, String perfil);

  UsuarioEntity updateImagemPerfil(Integer id, String imagemPerfil);

  void addPerfil(Integer usuarioId, String perfil);

  List<UsuarioEntity> findAll();

  UsuarioEntity findByEmail(String email);

  Page<UsuarioEntity> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

  UsuarioEntity fromDTO(UsuarioUpdateDTO objDto);

  UsuarioEntity fromDTO(UsuarioUpdateAdminDTO objDto);

  UsuarioEntity fromDTO(UsuarioNewDTO objDto);

  UsuarioDTO uploadProfilePicture(MultipartFile multipartFile);
}
