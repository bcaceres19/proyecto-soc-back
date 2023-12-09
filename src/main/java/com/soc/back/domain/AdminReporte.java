package com.soc.back.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminReporte {

    private Long idAdmin;

    private String codigoReporte;

    private LocalDateTime fechaRevision;
}
