package com.soc.back.adapter.out.persistence;

import com.soc.back.adapter.out.persistence.entity.AdminReporteEntity;
import com.soc.back.adapter.out.persistence.mapper.AdminReporteMapper;
import com.soc.back.adapter.out.persistence.repository.AdminReporteRepository;
import com.soc.back.application.port.in.command.AdminReporteCommand;
import com.soc.back.application.port.out.adminreporte.CrearAdminReportePort;
import com.soc.back.application.port.out.adminreporte.EliminarAdminReportePort;
import com.soc.back.application.port.out.reporte.AceptarReportePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class AdminReportePersistenceAdapter implements CrearAdminReportePort, EliminarAdminReportePort, AceptarReportePort {

    @Autowired
    private AdminReporteRepository adminReporteRepository;

    @Override
    public void crearAdminReporte(AdminReporteCommand command) {
        adminReporteRepository.save(AdminReporteMapper.INSTANCE.commandToEntity(command));
    }


    @Override
    public void eliminarAdminReporte(String codigo) {
        adminReporteRepository.deleteByCodigoReporte(codigo);
    }

    @Override
    public void aceptarReporte(String codigo) {
       adminReporteRepository.confirmarReporte(LocalDateTime.now(), codigo);
    }
}
