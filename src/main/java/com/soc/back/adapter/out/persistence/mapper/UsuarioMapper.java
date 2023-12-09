package com.soc.back.adapter.out.persistence.mapper;

import com.soc.back.adapter.out.persistence.entity.AdminEntity;
import com.soc.back.adapter.out.persistence.entity.UsuarioEntity;
import com.soc.back.domain.Admin;
import com.soc.back.domain.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioEntity domainToEntity(Usuario usuario);

}
