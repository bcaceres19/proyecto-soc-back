package com.soc.back.application.port.out.admin;

import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.domain.Admin;

public interface BuscarExistAdminPort {

    AdminUserCommand buscarExistAdmin(Admin admin);

}
