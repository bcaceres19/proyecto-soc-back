package com.soc.back.application.port.in.command;

import lombok.Data;

@Data
public class UsuarioCommand {

    private String nombre;

    private String apellido;

    private String email;

    private String password;

}
