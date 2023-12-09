package com.soc.back.application.service;

import com.soc.back.application.port.in.ReportePort;
import com.soc.back.application.port.in.command.ReporteCommand;
import com.soc.back.application.port.out.reporte.CrearReportePort;
import com.soc.back.application.port.out.usuario.CrearUsuarioPort;
import com.soc.back.common.enums.ExtensionEnum;
import com.soc.back.domain.Reporte;
import com.soc.back.domain.Usuario;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Service
@Log4j2
public class ReporteService implements ReportePort {

    @Autowired
    private CrearReportePort crearReportePort;

    @Override
    public void crearReporte(ReporteCommand comando) {
        Reporte reporte = new Reporte();
        reporte.setCodigoReporte(generarCodigoReporte());
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fechaFormateada = LocalDateTime.now().format(formato);
        Usuario usuario = new Usuario();
        comando.setFechaCreacion(LocalDateTime.now());
        String nombreArchivo = "Reporte"+ "-" + reporte.getCodigoReporte();
        StringBuffer txt = new StringBuffer(comando.getTitulo());
        txt
                .append("-")
                .append(fechaFormateada)
                .append("\n")
                .append(comando.getContenido())
                .append("\n")
                .append(usuario.getNombre());
        try{
            File archivo = new File(nombreArchivo);
            FileWriter writer = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(txt.toString());
            bw.flush();
            writer.close();
            byte[] contenido = Files.readAllBytes(Paths.get(archivo.getAbsolutePath()));
            String base64 = Base64.getEncoder().encodeToString(contenido);
            reporte.setFechaCreacion(comando.getFechaCreacion());
            reporte.setNombreReporte(nombreArchivo);
            reporte.setExtension(ExtensionEnum.TXT);
            reporte.setUsuarioCreacion(usuario.getId());
            reporte.setArchivo(base64);
            archivo.delete();
            crearReportePort.crearReporte(reporte);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
     }


    private String generarCodigoReporte(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0,5);
    }

}
