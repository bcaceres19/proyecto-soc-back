package com.soc.back.adapter.out.persistence;

import com.soc.back.adapter.out.persistence.mapper.ReporteMapper;
import com.soc.back.adapter.out.persistence.repository.ReporteRepository;
import com.soc.back.application.port.out.reporte.CrearReportePort;
import com.soc.back.domain.Reporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReportePersistenceAdapter implements CrearReportePort {

    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public void crearReporte(Reporte reporte) {
        reporteRepository.save(ReporteMapper.INSTANCE.domainToDocument(reporte));
    }
}
