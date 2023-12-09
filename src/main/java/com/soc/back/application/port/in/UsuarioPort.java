package com.soc.back.application.port.in;

import com.soc.back.application.port.in.command.UsuarioCommand;

public interface UsuarioPort {

    void crearUsuario(UsuarioCommand usuarioCommand);

}
