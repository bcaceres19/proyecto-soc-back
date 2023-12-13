package com.soc.back.adapter.out.persistence;

import com.soc.back.adapter.out.persistence.document.ReporteDocument;
import com.soc.back.adapter.out.persistence.mapper.ReporteMapper;
import com.soc.back.adapter.out.persistence.repository.AdminReporteRepository;
import com.soc.back.adapter.out.persistence.repository.ReporteRepository;
import com.soc.back.application.port.in.command.ReporteCommand;
import com.soc.back.application.port.out.adminreporte.BuscarConfirmacionReportePort;
import com.soc.back.application.port.out.reporte.*;
import com.soc.back.domain.Reporte;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class ReportePersistenceAdapterAdminAdmin implements CrearReportePort, BuscarReporteByUserPort, BuscarReporteByFechaAdminPort, BuscarReporteByCodigoAdminPort, ActualizarReportePort, BuscarReporteByCodigoUserPort, BuscarReporteByFechaUserPort, EliminarReportePort {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private BuscarConfirmacionReportePort buscarConfirmacionReportePort;

    @Override
    public void crearReporte(Reporte reporte) {
        reporteRepository.save(ReporteMapper.INSTANCE.domainToDocument(reporte));
    }

    @Override
    public List<ReporteCommand> buscarReporteByUser(Long idUsuario) {
        List<ReporteCommand> reportes = new ArrayList<>();
        reporteRepository.reportesUsuario(idUsuario).forEach(reporte -> {
            if(buscarConfirmacionReportePort.buscarConfirmacionReporte(reporte.getCodigoReporte())){
                reportes.add(reporte);
            }
        });
        return reportes;
    }

    @Override
    public List<ReporteCommand> buscarReporteByCodigo(String codigo) {
        return reporteRepository.reporteByCodigo(".*" + codigo + ".*");
    }

    @Override
    public List<ReporteCommand> buscarReporteByFecha(LocalDateTime fecha) {
        return reporteRepository.reporteByFecha(fecha);
    }

    @Override
    public void actualizarReporte(ReporteCommand command) {
        reporteRepository.save(ReporteMapper.INSTANCE.commandToDocument(command));
    }

    @Override
    public List<ReporteCommand> buscarReporteByCodigoUser(String codigo, Long idUser) {
        return reporteRepository.reporteByCodigoUser(".*" + codigo + ".*", idUser);
    }

    @Override
    public List<ReporteCommand> buscarReporteByFecha(LocalDateTime fecha, Long idUser) {
        return reporteRepository.reporteByFechaUser(fecha, idUser);

    }

    @Override
    public void eliminarReporte(String codigo) {
        ReporteDocument reporteDocument = new ReporteDocument();
        reporteDocument.setCodigoReporte(codigo);
        reporteRepository.delete(reporteDocument);
    }

}
