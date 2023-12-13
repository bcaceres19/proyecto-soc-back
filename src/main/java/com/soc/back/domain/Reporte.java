package com.soc.back.domain;

import com.soc.back.common.enums.ExtensionEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class Reporte {

    private String codigoReporte;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaCreacion;

    private String nombreReporte;

    private String tituloReporte;

    private String cuerpoGeneral;

    private String archivo;

    private String contenido;

    private String tipo;

    private ExtensionEnum extension;

    private Long usuarioCreacion;

}
