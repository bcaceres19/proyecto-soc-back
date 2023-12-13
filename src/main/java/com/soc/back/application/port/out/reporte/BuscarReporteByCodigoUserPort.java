package com.soc.back.application.port.out.reporte;

import com.soc.back.application.port.in.command.ReporteCommand;

import java.util.List;

public interface BuscarReporteByCodigoUserPort {

    List<ReporteCommand> buscarReporteByCodigoUser(String codigo, Long idUser);


}
