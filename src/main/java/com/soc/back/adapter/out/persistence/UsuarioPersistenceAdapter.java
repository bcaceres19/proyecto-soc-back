package com.soc.back.adapter.out.persistence;

import com.soc.back.adapter.out.persistence.mapper.UsuarioMapper;
import com.soc.back.adapter.out.persistence.repository.UsuarioRepository;
import com.soc.back.application.port.out.usuario.CrearUsuarioPort;
import com.soc.back.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioPersistenceAdapter implements CrearUsuarioPort {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void crearUsuario(Usuario usuario) {
        this.usuarioRepository.save(UsuarioMapper.INSTANCE.domainToEntity(usuario));
    }
}
