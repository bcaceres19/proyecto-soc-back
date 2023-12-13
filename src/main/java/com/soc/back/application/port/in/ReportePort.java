package com.soc.back.application.port.in;

import com.soc.back.application.port.in.command.ReporteCommand;
import com.soc.back.domain.Reporte;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportePort {

    void crearReporte(ReporteCommand comando);

    List<ReporteCommand> buscarReporteByUser(ReporteCommand command);

    List<ReporteCommand> buscarReporteByCodigo(String codigo, Long idAdmin, Long idUsuario);

    List<ReporteCommand> buscarReporteByFecha(LocalDateTime fecha, Long idAdmin, Long idUsuario);

    void actualizarReporte(ReporteCommand command);

    void eliminarReporte(String codigoReporte);

    void aceptarReporte(String codigoReporte);

}
