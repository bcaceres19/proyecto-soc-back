package com.soc.back.application.port.out.usuario;

import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.domain.Admin;
import com.soc.back.domain.Usuario;

public interface BuscarExistUserPort {
    AdminUserCommand buscarExistUser(Usuario usuario);


}
