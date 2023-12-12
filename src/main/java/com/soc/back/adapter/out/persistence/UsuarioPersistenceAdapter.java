package com.soc.back.adapter.out.persistence;

import com.soc.back.adapter.out.persistence.mapper.UsuarioMapper;
import com.soc.back.adapter.out.persistence.repository.UsuarioRepository;
import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.application.port.in.command.UsuarioCommand;
import com.soc.back.application.port.out.usuario.*;
import com.soc.back.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioPersistenceAdapter implements CrearUsuarioPort, BuscarExistUserPort, BuscarEmailUserPort, CambiarEstadoUserPort, TraerUsersPort, BuscarIdUsuarioPort {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void crearUsuario(Usuario usuario) {
        this.usuarioRepository.save(UsuarioMapper.INSTANCE.domainToEntity(usuario));
    }

    @Override
    public AdminUserCommand buscarExistUser(Usuario usuario) {
        return this.usuarioRepository.usuarioLog(usuario.getEmail(), usuario.getPassword());
    }

    @Override
    public boolean buscarEmailUserPort(String email) {
        return this.usuarioRepository.existsByEmail(email);

    }

    @Override
    public void cambiarEstadoUser(boolean estado, Long idUser) {
        this.usuarioRepository.actualizarEstado(estado, idUser);
    }

    @Override
    public List<UsuarioCommand> traerAllUser() {
        return usuarioRepository.buscarAllUsuarios();
    }

    @Override
    public UsuarioCommand buscarIdUsuarioPort(Long idUsuario) {
        return usuarioRepository.buscarIdUsuario(idUsuario);
    }
}
