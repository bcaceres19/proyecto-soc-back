package com.soc.back.adapter.out.persistence.mapper;

import com.soc.back.adapter.out.persistence.document.ReporteDocument;
import com.soc.back.domain.Reporte;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface ReporteMapper {

    ReporteMapper INSTANCE = Mappers.getMapper(ReporteMapper.class);

    ReporteDocument domainToDocument(Reporte reporte);

}
