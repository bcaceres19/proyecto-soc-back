package com.soc.back.application.port.out.admin;

import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.domain.Admin;

public interface CambiarEstadoAdminPort {

    void cambiarEstadoAdmin(boolean estado, Long idAdmin);


}
