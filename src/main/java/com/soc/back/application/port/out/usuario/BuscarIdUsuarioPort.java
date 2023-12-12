package com.soc.back.application.port.out.usuario;

import com.soc.back.application.port.in.command.UsuarioCommand;

public interface BuscarIdUsuarioPort {

    UsuarioCommand buscarIdUsuarioPort(Long idUsuario);

}
