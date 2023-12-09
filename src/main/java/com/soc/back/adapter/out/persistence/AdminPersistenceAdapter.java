package com.soc.back.adapter.out.persistence;


import com.soc.back.adapter.out.persistence.mapper.AdminMapper;
import com.soc.back.adapter.out.persistence.repository.AdminRepository;
import com.soc.back.application.port.out.admin.ActualizarAdminPort;
import com.soc.back.application.port.out.admin.CrearAdminPort;
import com.soc.back.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminPersistenceAdapter implements CrearAdminPort, ActualizarAdminPort {

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
}
