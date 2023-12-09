package com.soc.back.application.service;

import com.soc.back.adapter.out.persistence.entity.UsuarioEntity;
import com.soc.back.application.port.in.UsuarioPort;
import com.soc.back.application.port.in.command.UsuarioCommand;
import com.soc.back.application.port.out.usuario.CrearUsuarioPort;
import com.soc.back.domain.Usuario;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2
public class UsuarioService implements UsuarioPort {

    @Autowired
    private CrearUsuarioPort crearUsuarioPort;

    @Override
    public void crearUsuario(UsuarioCommand usuarioCommand) {
        Usuario usuario =  new Usuario();
        usuario.setNombre(usuarioCommand.getNombreUsuario());
        usuario.setFechaCreacion(LocalDateTime.now());
        crearUsuarioPort.crearUsuario(usuario);
    }
}
