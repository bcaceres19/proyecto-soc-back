package com.soc.back.adapter.out.persistence.repository;

import com.soc.back.adapter.out.persistence.document.ReporteDocument;
import com.soc.back.application.port.in.command.ReporteCommand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReporteRepository extends MongoRepository<ReporteDocument,String> {

    @Query("{'usuarioCreacion': ?0}")
    List<ReporteCommand> reportesUsuario(Long idUsuario);

    @Query("{'fechaCreacion':{$gte:?0}, 'usuarioCreacion': ?1}")
    List<ReporteCommand> reporteByFechaUser(LocalDateTime fecha, Long idUsuario);

    @Query("{'fechaCreacion':{$gte:?0}}")
    List<ReporteCommand> reporteByFecha(LocalDateTime fecha);

    @Query("{'_id':{'$regex':?0, '$options': 'i'}, 'usuarioCreacion': ?1}")
    List<ReporteCommand> reporteByCodigoUser(String  patron, Long idUsuario);

    @Query("{'_id':{'$regex':?0, '$options': 'i'}}")
    List<ReporteCommand> reporteByCodigo(String  patron);

}
