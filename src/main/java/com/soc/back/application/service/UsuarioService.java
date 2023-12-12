package com.soc.back.application.service;

import com.soc.back.adapter.out.persistence.entity.UsuarioEntity;
import com.soc.back.application.port.in.UsuarioPort;
import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.application.port.in.command.UsuarioCommand;
import com.soc.back.application.port.out.usuario.*;
import com.soc.back.domain.Admin;
import com.soc.back.domain.Usuario;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class UsuarioService implements UsuarioPort {

    @Autowired
    private CrearUsuarioPort crearUsuarioPort;

    @Autowired
    private BuscarExistUserPort buscarExistUserPort;

    @Autowired
    private BuscarEmailUserPort buscarEmailUserPort;

    @Autowired
    private CambiarEstadoUserPort cambiarEstadoUserPort;

    @Autowired
    private TraerUsersPort traerUsersPort;


    @Override
    public void crearUsuario(UsuarioCommand usuarioCommand) {
        Usuario usuario =  new Usuario();
        usuario.setNombre(usuarioCommand.getNombre());
        usuario.setApellido(usuarioCommand.getApellido());
        usuario.setEmail(usuarioCommand.getEmail());
        usuario.setPassword(usuarioCommand.getPassword());
        usuario.setFechaCreacion(LocalDateTime.now());
        crearUsuarioPort.crearUsuario(usuario);
    }

    @Override
    public AdminUserCommand buscarExistUser(AdminUserCommand command) {
        Usuario usuario = new Usuario();
        usuario.setEmail(command.getEmail());
        usuario.setPassword(command.getPassword());
        return buscarExistUserPort.buscarExistUser(usuario);
    }

    @Override
    public boolean buscarEmailUsuario(String email) {
        return buscarEmailUserPort.buscarEmailUserPort(email);
    }

    @Override
    public void cambiarEstadoUser(boolean estado, Long idUser) {
        cambiarEstadoUserPort.cambiarEstadoUser(estado, idUser);
    }

    @Override
    public List<UsuarioCommand> traerAllUsuarios() {
        return traerUsersPort.traerAllUser();
    }
}
