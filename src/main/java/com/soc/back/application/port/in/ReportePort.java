package com.soc.back.application.port.in;

import com.soc.back.application.port.in.command.ReporteCommand;
import com.soc.back.domain.Reporte;

import java.util.List;

public interface ReportePort {

    void crearReporte(ReporteCommand comando);

    List<ReporteCommand> buscarReporteByUser(ReporteCommand command);


}
