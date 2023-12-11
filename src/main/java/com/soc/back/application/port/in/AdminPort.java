package com.soc.back.application.port.in;


import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.in.command.AdminUserCommand;

public interface AdminPort {

    void crearAdmin(AdminCommand command);

    void actualizarAdmin(AdminCommand command);

    AdminUserCommand buscarExistAdmin(AdminUserCommand command);

    boolean buscarEmailAdmin(String command);

}
