package com.soc.back.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(schema = "public", name = "admin_reporte")
public class AdminReporteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "codigo_reporte")
    private String codigoReporte;

    @Column(name = "fecha_revision")
    private LocalDateTime fechaRevision;

    @ManyToOne
    @JoinColumn(name = "id_admin")
    private AdminEntity admin;
}
