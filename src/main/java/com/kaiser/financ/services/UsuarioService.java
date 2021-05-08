package com.kaiser.financ.services;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kaiser.financ.domain.Usuario;
import com.kaiser.financ.domain.enums.Perfil;
import com.kaiser.financ.dto.UsuarioDTO;
import com.kaiser.financ.dto.UsuarioNewDTO;
import com.kaiser.financ.dto.UsuarioUpdateAdminDTO;
import com.kaiser.financ.dto.UsuarioUpdateDTO;
import com.kaiser.financ.repositories.UsuarioRepository;
import com.kaiser.financ.security.UserSS;
import com.kaiser.financ.services.exceptions.AuthorizationException;
import com.kaiser.financ.services.exceptions.DataIntegrityException;
import com.kaiser.financ.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {		
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private UsuarioRepository repo;	
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e) {
			return null;
		}		
	}
	
	public Usuario userLoggedIn() {
		try {
			UserSS userSS = (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Optional<Usuario> obj = repo.findById(userSS.getId());
			return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + userSS.getId() + ", Tipo: " + Usuario.class.getName())); 
		}
		catch(Exception e) {
			return null;
		}		
	}
	
	public Usuario find(Integer id) {
		
		UserSS user = UsuarioService.authenticated();
		if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Usuario> obj = repo.findById(id);		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}		
	
	@Transactional
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		obj = repo.save(obj);		
		return obj;		
	}
	
	public Usuario update(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);        
		return repo.save(newObj);
	}
	
	public Usuario updateAdmin(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateDataAdmin(newObj, obj);        
		return repo.save(newObj);
	}
	
	public Usuario updateImagemPerfil(Integer id, String imagemPerfil) {
		Usuario obj = find(id);
		obj.setImagemPerfil(imagemPerfil);		        
		return repo.save(obj);
	}	
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente que possui Pedidos.");			
		}		
	}
	
	public List<Usuario> findAll(){
		return repo.findAll();
	}
	
	public Usuario findByEmail(String email) {		
		UserSS user = UsuarioService.authenticated();
		if(user==null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}		 
		
		Usuario obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Usuario.class.getName());
		}
		return obj;
	}
	
	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);		
	}	
	
	public Usuario fromDTO(UsuarioDTO objDto) {
		return new Usuario(objDto.getId(), objDto.getNome(), objDto.getSobrenome(), objDto.getEmail(), null);
	}
	
	public Usuario fromDTO(UsuarioUpdateDTO objDto) {
		return new Usuario(objDto.getNome(), objDto.getSobrenome(), objDto.getDtNascimento(), objDto.getCidade(), objDto.getEstado(), objDto.getDescricao());
	}
	
	public Usuario fromDTO(UsuarioUpdateAdminDTO objDto) {
		return new Usuario(objDto.getEmail(), objDto.getNome(), objDto.getSobrenome(), objDto.getDtNascimento(), objDto.getCidade(), objDto.getEstado(), objDto.getDescricao(), objDto.getImagemPerfil());
	}
	
	public Usuario fromDTO(UsuarioNewDTO objDto) {
		Usuario cli = new Usuario(null, objDto.getNome(), objDto.getSobrenome(), objDto.getEmail(), pe.encode(objDto.getSenha()));
		
		cli.getTelefones().add(objDto.getTelefone1());
		if( objDto.getTelefone2() != null ) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if( objDto.getTelefone3() != null ) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setSobrenome(obj.getSobrenome());
		newObj.setCidade(obj.getCidade());
		newObj.setEstado(obj.getEstado());
		newObj.setDtNascimento(obj.getDtNascimento());
		newObj.setDescricao(obj.getDescricao());
	}
	
	private void updateDataAdmin(Usuario newObj, Usuario obj) {
		newObj.setEmail(obj.getEmail());
		newObj.setNome(obj.getNome());
		newObj.setSobrenome(obj.getSobrenome());
		newObj.setCidade(obj.getCidade());
		newObj.setEstado(obj.getEstado());
		newObj.setDtNascimento(obj.getDtNascimento());
		newObj.setDescricao(obj.getDescricao());
		newObj.setImagemPerfil(obj.getImagemPerfil());
	}
	
	public UsuarioDTO uploadProfilePicture(MultipartFile multipartFile) {		
		UserSS user = UsuarioService.authenticated();
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
	
}
