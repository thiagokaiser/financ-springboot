package com.kaiser.financ.repositories;

import com.kaiser.financ.entities.UsuarioEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

  @Transactional(readOnly = true)
  UsuarioEntity findByEmail(String email);

  @Transactional
  @Modifying
  @Query("UPDATE Usuario SET last_login = ?2 where id = ?1")
  void updateLastLogin(Integer usuarioId, LocalDateTime lastLogin);
}
