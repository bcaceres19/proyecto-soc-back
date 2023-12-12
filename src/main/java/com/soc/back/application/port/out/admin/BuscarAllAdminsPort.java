package com.soc.back.application.port.out.admin;

import com.soc.back.application.port.in.command.AdminCommand;

import java.util.List;

public interface BuscarAllAdminsPort {

    List<AdminCommand> buscarAllAdmins();

}
