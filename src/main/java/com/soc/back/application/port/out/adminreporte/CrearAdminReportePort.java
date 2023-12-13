package com.soc.back.application.port.out.adminreporte;

import com.soc.back.application.port.in.command.AdminReporteCommand;

public interface CrearAdminReportePort {

    void crearAdminReporte(AdminReporteCommand command);

}
