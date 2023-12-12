package com.soc.back.application.port.in;

import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.application.port.in.command.UsuarioCommand;

import java.util.List;

public interface UsuarioPort {

    void crearUsuario(UsuarioCommand usuarioCommand);

    AdminUserCommand buscarExistUser(AdminUserCommand command);

    boolean buscarEmailUsuario(String command);

    void cambiarEstadoUser(boolean estado, Long idUser);

    List<UsuarioCommand> traerAllUsuarios();


}
