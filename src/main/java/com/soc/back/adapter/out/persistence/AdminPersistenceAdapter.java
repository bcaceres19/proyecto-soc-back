package com.soc.back.adapter.out.persistence;


import com.soc.back.adapter.out.persistence.mapper.AdminMapper;
import com.soc.back.adapter.out.persistence.repository.AdminRepository;
import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.application.port.out.admin.*;
import com.soc.back.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminPersistenceAdapter implements CrearAdminPort, ActualizarAdminPort, BuscarExistAdminPort, BuscarEmailAdminPort, CambiarEstadoAdminPort, BuscarAllAdminsPort {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void actualizarAdmin(Admin admin) {
        this.adminRepository.save(AdminMapper.INSTANCE.domainToEntity(admin));
    }

    @Override
    public void crearAdmin(Admin admin) {
        this.adminRepository.save(AdminMapper.INSTANCE.domainToEntity(admin));
    }

    @Override
    public AdminUserCommand buscarExistAdmin(Admin admin) {
        return this.adminRepository.adminLog(admin.getEmail(), admin.getPassword());
    }

    @Override
    public boolean buscarEmailAdminPort(String email) {
        return this.adminRepository.existsByEmail(email);
    }

    @Override
    public void cambiarEstadoAdmin(boolean estado, Long idAdmin) {
        this.adminRepository.actualizarEstado(estado, idAdmin);
    }

    @Override
    public List<AdminCommand> buscarAllAdmins() {
        return this.adminRepository.buscarAllUsuarios();
    }
}
