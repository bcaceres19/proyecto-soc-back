package com.soc.back.application.port.out.reporte;

import com.soc.back.application.port.in.command.ReporteCommand;

import java.util.List;

public interface BuscarReporteByUserPort {

    List<ReporteCommand> buscarReporteByUser(Long idUsuario);

}
