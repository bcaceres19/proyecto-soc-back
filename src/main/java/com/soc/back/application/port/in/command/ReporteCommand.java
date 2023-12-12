package com.soc.back.application.port.in.command;

import com.soc.back.common.enums.ExtensionEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ReporteCommand {

    private String titulo;

    private String contenido;

    private Long usuarioCreacion;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaCreacion;

    private String codigoReporte;

    private String nombreReporte;

    private String archivo;

    @Enumerated(EnumType.STRING)
    private ExtensionEnum extension;

    private String tipo;


}
