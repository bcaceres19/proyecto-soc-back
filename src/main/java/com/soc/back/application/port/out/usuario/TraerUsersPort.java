package com.soc.back.application.port.out.usuario;

import com.soc.back.application.port.in.command.UsuarioCommand;

import java.util.List;

public interface TraerUsersPort {

    List<UsuarioCommand> traerAllUser();

}
