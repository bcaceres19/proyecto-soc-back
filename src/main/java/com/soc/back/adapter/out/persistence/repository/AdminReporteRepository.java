package com.soc.back.adapter.out.persistence.repository;

import com.soc.back.adapter.out.persistence.entity.AdminReporteEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AdminReporteRepository extends JpaRepository<AdminReporteEntity, Long> {

    @Transactional
    void deleteByCodigoReporte(String codigoReporte);

    @Modifying
    @Query(value = "UPDATE admin_reporte SET fecha_revision = :fecha where codigo_reporte=:codigo", nativeQuery = true)
    @Transactional
    void confirmarReporte(@Param("fecha") LocalDateTime fecha, @Param("codigo") String codigo);

    @Query(value = "SELECT\n" +
            "  CASE\n" +
            "    WHEN ar.fecha_revision is null THEN 'true'\n" +
            "    ELSE 'false'\n" +
            "  END AS resultado\n" +
            "FROM\n" +
            "  admin_reporte ar\n" +
            "WHERE\n" +
            "   ar.codigo_reporte = :codigo", nativeQuery = true)
    String buscarReporteConfirmado(@Param("codigo") String codigo);
}
