package com.kaiser.financ.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.dto.UsuarioDTO;
import com.kaiser.financ.dto.UsuarioNewDTO;
import com.kaiser.financ.dto.UsuarioUpdateAdminDTO;
import com.kaiser.financ.dto.UsuarioUpdateDTO;

public interface UsuarioService {

    Usuario userLoggedIn();

    Usuario find(Integer id);

    Usuario insert(Usuario obj);

    Usuario update(Usuario obj);

    Usuario updateAdmin(Usuario obj);

    void delete(Integer id);

    void removePerfil(Integer usuarioId, String perfil);

    Usuario updateImagemPerfil(Integer id, String imagemPerfil);

    void addPerfil(Integer usuarioId, String perfil);

    List<Usuario> findAll();

    Usuario findByEmail(String email);

    Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

    Usuario fromDTO(UsuarioUpdateDTO objDto);

    Usuario fromDTO(UsuarioUpdateAdminDTO objDto);

    Usuario fromDTO(UsuarioNewDTO objDto);

    UsuarioDTO uploadProfilePicture(MultipartFile multipartFile);

}
