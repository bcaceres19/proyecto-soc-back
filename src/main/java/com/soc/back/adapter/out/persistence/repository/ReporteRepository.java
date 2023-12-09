package com.soc.back.adapter.out.persistence.repository;

import com.soc.back.adapter.out.persistence.document.ReporteDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReporteRepository extends MongoRepository<ReporteDocument,String> {
}
