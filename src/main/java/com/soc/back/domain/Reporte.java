package com.soc.back.domain;

import com.soc.back.common.enums.ExtensionEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reporte {

    private String codigoReporte;

    private LocalDateTime fechaCreacion;

    private String nombreReporte;

    private String archivo;

    private ExtensionEnum extension;

    private Long usuarioCreacion;

}
