package com.soc.back.application.port.in.command;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReporteCommand {

    private String titulo;

    private String contenido;

    private Long idUsuarioCreacion;

    private LocalDateTime fechaCreacion;

}
