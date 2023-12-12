package com.soc.back.application.port.in.command;

import lombok.Data;

@Data
public class UsuarioCommand {

    public UsuarioCommand(String email, Boolean actividad) {
        this.email = email;
        this.actividad = actividad;
    }

    public UsuarioCommand(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    private String nombre;

    private String apellido;

    private String email;

    private String password;

    private Boolean actividad;

    @Override
    public String toString() {
        return "UsuarioCommand{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", actividad=" + actividad +
                '}';
    }
}
