package com.soc.back.application.port.in.command;

import com.soc.back.adapter.out.persistence.entity.AdminEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminReporteCommand {

    private Long id;

    private String codigoReporte;

    private LocalDateTime fechaRevision;


    private AdminEntity admin;

}
