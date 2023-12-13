package com.soc.back.adapter.out.persistence.repository;

import com.soc.back.adapter.out.persistence.entity.AdminEntity;
import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.application.port.in.command.UsuarioCommand;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    @Query("SELECT new com.soc.back.application.port.in.command.AdminUserCommand(u.id, u.email, true , false) FROM AdminEntity u where u.email=:email and u.password=:password")
    AdminUserCommand adminLog(@Param("email") String email, @Param("password") String password);

    boolean existsByEmail(String email);
    @Modifying
    @Query(value = "UPDATE admin SET actividad=:estado where id=:idAdmin ", nativeQuery = true)
    @Transactional
    void actualizarEstado(@Param("estado") boolean estado, @Param("idAdmin") Long idAdmin);

    @Query("SELECT new com.soc.back.application.port.in.command.AdminCommand(a.nombre, a.actividad) FROM AdminEntity a ")
    List<AdminCommand> buscarAllUsuarios();

    @Query(value = "SELECT u.id as admin_id, COUNT(ar.id_admin) as cantidad_registros\n" +
            "FROM admin u\n" +
            "left JOIN admin_reporte ar ON u.id = ar.id_admin\n" +
            "GROUP BY u.id order by cantidad_registros ASC", nativeQuery = true)
    List<Object[]> adminDisponible();

    @Query(value = "select ar.codigo_reporte from admin a join admin_reporte ar on a.id = ar.id_admin where a.id = :idAdmin and ar.fecha_revision is null", nativeQuery = true)
    List<Object[]>  reportesAdmin(@Param("idAdmin") Long idAdmin);
}
