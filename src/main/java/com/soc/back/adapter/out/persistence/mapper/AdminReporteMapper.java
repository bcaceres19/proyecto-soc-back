package com.soc.back.adapter.out.persistence.mapper;

import com.soc.back.adapter.out.persistence.entity.AdminReporteEntity;
import com.soc.back.application.port.in.command.AdminReporteCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface AdminReporteMapper {

    AdminReporteMapper INSTANCE = Mappers.getMapper(AdminReporteMapper.class);

    AdminReporteEntity commandToEntity(AdminReporteCommand command);
}
