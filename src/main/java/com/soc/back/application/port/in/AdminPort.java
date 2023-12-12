package com.soc.back.application.port.in;


import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.in.command.AdminUserCommand;

import java.util.List;

public interface AdminPort {

    void crearAdmin(AdminCommand command);

    void actualizarAdmin(AdminCommand command);

    AdminUserCommand buscarExistAdmin(AdminUserCommand command);

    boolean buscarEmailAdmin(String command);

    void cambiaEstado(boolean estado, Long idAdmin);

    List<AdminCommand> traerAllAdmins();



}
