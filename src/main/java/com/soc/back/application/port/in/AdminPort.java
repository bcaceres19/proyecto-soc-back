package com.soc.back.application.port.in;


import com.soc.back.application.port.in.command.AdminCommand;

public interface AdminPort {

    void crearAdmin(AdminCommand command);

    void actualizarAdmin(AdminCommand command);

}
