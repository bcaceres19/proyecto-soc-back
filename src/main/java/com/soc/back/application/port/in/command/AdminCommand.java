package com.soc.back.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCommand {

    public AdminCommand(String nombre, Boolean actividad) {
        this.nombre = nombre;
        this.actividad = actividad;
    }

    private Long id;

    private String nombre;

    private String apellido;

    private String email;

    private String password;

    private LocalDateTime fechaCreacion;

    private Boolean actividad;


}
