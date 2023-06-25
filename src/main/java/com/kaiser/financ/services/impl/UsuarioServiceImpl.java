package com.kaiser.financ.services.impl;

import com.kaiser.financ.dtos.UsuarioDTO;
import com.kaiser.financ.dtos.UsuarioNewDTO;
import com.kaiser.financ.dtos.UsuarioUpdateAdminDTO;
import com.kaiser.financ.dtos.UsuarioUpdateDTO;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.entities.enums.PerfilEnum;
import com.kaiser.financ.repositories.UsuarioRepository;
import com.kaiser.financ.security.UserSS;
import com.kaiser.financ.services.AmazonS3Service;
import com.kaiser.financ.services.ImageService;
import com.kaiser.financ.services.UsuarioService;
import com.kaiser.financ.services.exceptions.AuthorizationException;
import com.kaiser.financ.services.exceptions.DataIntegrityException;
import com.kaiser.financ.services.exceptions.ObjectNotFoundException;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

  @Autowired
  private BCryptPasswordEncoder pe;

  @Autowired
  private UsuarioRepository repo;

  @Autowired
  private AmazonS3Service s3Service;

  @Autowired
  private ImageService imageService;

  @Value("${img.prefix.client.profile}")
  private String prefix;

  @Value("${img.profile.size}")
  private Integer size;

  public static UserSS authenticated() {
    try {
      return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UsuarioEntity usuario = repo.findByEmail(email);
    if (usuario == null) {
      throw new UsernameNotFoundException(email);
    }
    return new UserSS(
        usuario.getId(),
        usuario.getEmail(),
        usuario.getSenha(),
        usuario.getPerfis(),
        usuario.getNome(),
        usuario.getSobrenome());
  }

  @Override
  public UsuarioEntity userLoggedIn() {
    try {
      UserSS userSS =
          (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Optional<UsuarioEntity> obj = repo.findById(userSS.getId());
      return obj.orElseThrow(
          () ->
              new ObjectNotFoundException(
                  "Objeto não encontrado! Id: "
                      + userSS.getId()
                      + ", Tipo: "
                      + UsuarioEntity.class.getName()));
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public UsuarioEntity find(Integer id) {

    UserSS user = UsuarioServiceImpl.authenticated();
    if (user == null || !user.hasRole(PerfilEnum.ADMIN) && !id.equals(user.getId())) {
      throw new AuthorizationException("Acesso negado");
    }

    Optional<UsuarioEntity> obj = repo.findById(id);
    return obj.orElseThrow(
        () ->
            new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + UsuarioEntity.class.getName()));
  }

  @Override
  @Transactional
  public UsuarioEntity insert(UsuarioEntity obj) {
    obj.setId(null);
    obj = repo.save(obj);
    return obj;
  }

  @Override
  public UsuarioEntity update(UsuarioEntity obj) {
    UsuarioEntity newObj = find(obj.getId());
    updateData(newObj, obj);
    return repo.save(newObj);
  }

  @Override
  public UsuarioEntity updateAdmin(UsuarioEntity obj) {
    UsuarioEntity newObj = find(obj.getId());
    updateDataAdmin(newObj, obj);
    return repo.save(newObj);
  }

  @Override
  public UsuarioEntity updateImagemPerfil(Integer id, String imagemPerfil) {
    UsuarioEntity obj = find(id);
    obj.setImagemPerfil(imagemPerfil);
    return repo.save(obj);
  }

  @Override
  public void delete(Integer id) {
    find(id);
    try {
      repo.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException("Não é possivel excluir um cliente que possui Pedidos.");
    }
  }

  @Override
  public void removePerfil(Integer usuarioId, String perfil) {
    UsuarioEntity obj = find(usuarioId);
    PerfilEnum perfilAux = PerfilEnum.valueOf(perfil);
    obj.removePerfil(perfilAux);
    repo.save(obj);
  }

  @Override
  public void addPerfil(Integer usuarioId, String perfil) {
    UsuarioEntity obj = find(usuarioId);
    PerfilEnum perfilAux = PerfilEnum.valueOf(perfil);
    obj.addPerfil(perfilAux);
    repo.save(obj);
  }

  @Override
  public List<UsuarioEntity> findAll() {
    return repo.findAll();
  }

  @Override
  public UsuarioEntity findByEmail(String email) {
    UserSS user = UsuarioServiceImpl.authenticated();
    if (user == null || !user.hasRole(PerfilEnum.ADMIN) && !email.equals(user.getUsername())) {
      throw new AuthorizationException("Acesso negado");
    }

    UsuarioEntity obj = repo.findByEmail(email);
    if (obj == null) {
      throw new ObjectNotFoundException(
          "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + UsuarioEntity.class.getName());
    }
    return obj;
  }

  @Override
  public Page<UsuarioEntity> findPage(
      Integer page, Integer linesPerPage, String orderBy, String direction) {
    PageRequest pageRequest =
        PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    return repo.findAll(pageRequest);
  }

  @Override
  public UsuarioEntity fromDTO(UsuarioUpdateDTO objDto) {
    return new UsuarioEntity(objDto);
  }

  @Override
  public UsuarioEntity fromDTO(UsuarioUpdateAdminDTO objDto) {
    return new UsuarioEntity(objDto);
  }

  @Override
  public UsuarioEntity fromDTO(UsuarioNewDTO objDto) {
    UsuarioEntity cli =
        new UsuarioEntity(
            null,
            objDto.getNome(),
            objDto.getSobrenome(),
            objDto.getEmail(),
            pe.encode(objDto.getSenha()));

    cli.getTelefones().add(objDto.getTelefone1());
    if (objDto.getTelefone2() != null) {
      cli.getTelefones().add(objDto.getTelefone2());
    }
    if (objDto.getTelefone3() != null) {
      cli.getTelefones().add(objDto.getTelefone3());
    }
    return cli;
  }

  @Override
  public UsuarioDTO uploadProfilePicture(MultipartFile multipartFile) {
    UserSS user = UsuarioServiceImpl.authenticated();
    if (user == null) {
      throw new AuthorizationException("Acesso negado");
    }

    BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
    jpgImage = imageService.cropSquare(jpgImage);
    jpgImage = imageService.resize(jpgImage, size);

    String fileName = prefix + user.getId() + ".jpg";
    URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

    UsuarioDTO objDto = new UsuarioDTO(updateImagemPerfil(user.getId(), uri.toString()));

    return objDto;
  }

  private void updateData(UsuarioEntity newObj, UsuarioEntity obj) {
    newObj.setNome(obj.getNome());
    newObj.setSobrenome(obj.getSobrenome());
    newObj.setCidade(obj.getCidade());
    newObj.setEstado(obj.getEstado());
    newObj.setDtNascimento(obj.getDtNascimento());
    newObj.setDescricao(obj.getDescricao());
  }

  private void updateDataAdmin(UsuarioEntity newObj, UsuarioEntity obj) {
    newObj.setEmail(obj.getEmail());
    newObj.setNome(obj.getNome());
    newObj.setSobrenome(obj.getSobrenome());
    newObj.setCidade(obj.getCidade());
    newObj.setEstado(obj.getEstado());
    newObj.setDtNascimento(obj.getDtNascimento());
    newObj.setDescricao(obj.getDescricao());
    newObj.setImagemPerfil(obj.getImagemPerfil());
  }
}
