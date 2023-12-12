package com.soc.back.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class Admin {


    public Admin(Long id, String nombre, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.fechaActualizacion = fechaActualizacion;
    }

    private Long id;

    private String nombre;

    private String apellido;

    private String email;

    private String password;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    private boolean actividad;


}

