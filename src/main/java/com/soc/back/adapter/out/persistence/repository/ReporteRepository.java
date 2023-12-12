package com.soc.back.adapter.out.persistence.repository;

import com.soc.back.adapter.out.persistence.document.ReporteDocument;
import com.soc.back.application.port.in.command.ReporteCommand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReporteRepository extends MongoRepository<ReporteDocument,String> {

    @Query("{'usuarioCreacion': ?0}")
    List<ReporteCommand> reportesUsuario(Long idUsuario);

}
