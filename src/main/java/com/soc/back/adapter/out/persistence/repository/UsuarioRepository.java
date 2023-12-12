package com.soc.back.adapter.out.persistence.repository;

import com.soc.back.adapter.out.persistence.entity.UsuarioEntity;
import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.application.port.in.command.UsuarioCommand;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query("SELECT new com.soc.back.application.port.in.command.AdminUserCommand(u.id, u.email, false , true) FROM UsuarioEntity u where u.email=:email and u.password=:password")
    AdminUserCommand usuarioLog(@Param("email") String email, @Param("password") String password);

    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "UPDATE usuario SET actividad=:estado where id=:idUsuario", nativeQuery = true)
    @Transactional
    void actualizarEstado(@Param("estado") boolean estado, @Param("idUsuario") Long idUsuario);

    @Query("SELECT new com.soc.back.application.port.in.command.UsuarioCommand(u.email, u.actividad) FROM UsuarioEntity u ")
    List<UsuarioCommand> buscarAllUsuarios();

    @Query("SELECT new com.soc.back.application.port.in.command.UsuarioCommand(u.nombre, u.apellido) FROM UsuarioEntity u where u.id=:idUsuario")
    UsuarioCommand buscarIdUsuario(@Param("idUsuario")Long idUsuario);

}
