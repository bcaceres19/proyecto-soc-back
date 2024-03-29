package com.soc.back.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Usuario {

    private Long id;

    private String nombre;

    private String apellido;

    private boolean actividad;

    private String email;

    private String password;

    private LocalDateTime fechaCreacion;

}
