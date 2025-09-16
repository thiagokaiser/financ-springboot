package com.kaiser.financ.repositories;

import com.kaiser.financ.entities.UsuarioEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

  @Transactional(readOnly = true)
  UsuarioEntity findByEmail(String email);

  @Modifying
  @Transactional
  @Query("UPDATE Usuario u SET u.lastLogin = :lastLogin WHERE u.id = :id")
  void updateLastLogin(@Param("id") Integer id, @Param("lastLogin") LocalDateTime lastLogin);

}
