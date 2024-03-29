package com.soc.back.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@SequenceGenerator(name = "secuencia_usuarios", sequenceName = "secuencia_usuarios", allocationSize = 1)
@Data
public abstract class UsuarioGeneralEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "actividad")
    private boolean actividad;

}
