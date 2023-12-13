package com.soc.back.application.port.out.reporte;

import com.soc.back.application.port.in.command.ReporteCommand;

import java.time.LocalDateTime;
import java.util.List;

public interface BuscarReporteByFechaUserPort {

    List<ReporteCommand> buscarReporteByFecha(LocalDateTime fecha, Long idUser);


}
