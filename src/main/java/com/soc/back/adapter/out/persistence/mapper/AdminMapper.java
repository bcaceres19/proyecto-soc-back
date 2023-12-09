package com.soc.back.adapter.out.persistence.mapper;

import com.soc.back.adapter.out.persistence.entity.AdminEntity;
import com.soc.back.domain.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface AdminMapper  {

    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    Admin entityToDomain(AdminEntity entity);

    AdminEntity domainToEntity(Admin admin);

}
