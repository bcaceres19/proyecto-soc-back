package com.soc.back.application.port.out.reporte;

import com.soc.back.application.port.in.command.ReporteCommand;

public interface ActualizarReportePort {

    void actualizarReporte(ReporteCommand command);

}
