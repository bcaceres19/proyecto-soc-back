package com.soc.back.application.service;


import com.soc.back.application.port.in.AdminPort;
import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.application.port.out.admin.*;
import com.soc.back.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService implements AdminPort {


    @Autowired
    private CrearAdminPort crearAdminPort;

    @Autowired
    private ActualizarAdminPort actualizarAdminPort;

    @Autowired
    private BuscarExistAdminPort buscarExistAdminPort;

    @Autowired
    private BuscarEmailAdminPort buscarEmailAdminPort;

    @Autowired
    private CambiarEstadoAdminPort cambiarEstadoAdminPort;

    @Autowired
    private BuscarAllAdminsPort buscarAllAdminsPort;

    @Override
    public void crearAdmin(AdminCommand command) {
        Admin admin = new Admin();
        admin.setNombre(command.getNombre());
        admin.setApellido(command.getApellido());
        admin.setEmail(command.getEmail());
        admin.setPassword(command.getPassword());
        admin.setFechaCreacion(LocalDateTime.now());
        crearAdminPort.crearAdmin(admin);
    }

    @Override
    public void actualizarAdmin(AdminCommand command) {
        Admin admin = new Admin(command.getIdAdmin(), command.getNombre(), LocalDateTime.now());
        actualizarAdminPort.actualizarAdmin(admin);
    }

    @Override
    public AdminUserCommand buscarExistAdmin(AdminUserCommand command) {
        Admin admin = new Admin();
        admin.setEmail(command.getEmail());
        admin.setPassword(command.getPassword());
        return buscarExistAdminPort.buscarExistAdmin(admin);
    }

    @Override
    public boolean buscarEmailAdmin(String email) {
        return buscarEmailAdminPort.buscarEmailAdminPort(email);
    }

    @Override
    public void cambiaEstado(boolean estado, Long idAdmin) {
        this.cambiarEstadoAdminPort.cambiarEstadoAdmin(estado, idAdmin);
    }

    @Override
    public List<AdminCommand> traerAllAdmins() {
        return buscarAllAdminsPort.buscarAllAdmins();
    }
}
