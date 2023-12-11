package com.soc.back.adapter.out.persistence.repository;

import com.soc.back.adapter.out.persistence.entity.UsuarioEntity;
import com.soc.back.application.port.in.command.AdminUserCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query("SELECT new com.soc.back.application.port.in.command.AdminUserCommand(u.id, u.email, false , true) FROM UsuarioEntity u where u.email=:email and u.password=:password")
    AdminUserCommand usuarioLog(@Param("email") String email, @Param("password") String password);

    boolean existsByEmail(String email);

}
