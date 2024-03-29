package com.soc.back.adapter.out.persistence.document;


import com.soc.back.common.enums.ExtensionEnum;
import com.soc.back.domain.Usuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reportes")
public class ReporteDocument {

    @Id
    private String codigoReporte;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaCreacion;

    private String nombreReporte;

    private String contenidoArchivo;

    private String tituloReporte;

    private String cuerpoGeneral;

    private String contenido;

    private String archivo;

    private String tipo;

    @Enumerated(EnumType.STRING)
    private ExtensionEnum extension;

    private Long usuarioCreacion;

}
