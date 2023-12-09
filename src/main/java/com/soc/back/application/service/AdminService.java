package com.soc.back.application.service;


import com.soc.back.application.port.in.AdminPort;
import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.out.admin.ActualizarAdminPort;
import com.soc.back.application.port.out.admin.CrearAdminPort;
import com.soc.back.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminService implements AdminPort {


    @Autowired
    private CrearAdminPort crearAdminPort;

    @Autowired
    private ActualizarAdminPort actualizarAdminPort;

    @Override
    public void crearAdmin(AdminCommand command) {
        Admin admin = new Admin();
        admin.setNombre(command.getNombre());
        admin.setFechaCreacion(LocalDateTime.now());
        crearAdminPort.crearAdmin(admin);
    }

    @Override
    public void actualizarAdmin(AdminCommand command) {
        Admin admin = new Admin(command.getIdAdmin(), command.getNombre(), LocalDateTime.now());
        actualizarAdminPort.actualizarAdmin(admin);
    }
}
