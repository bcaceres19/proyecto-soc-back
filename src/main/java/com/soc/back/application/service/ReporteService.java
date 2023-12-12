package com.soc.back.application.service;

import com.soc.back.application.port.in.ReportePort;
import com.soc.back.application.port.in.command.ReporteCommand;
import com.soc.back.application.port.in.command.UsuarioCommand;
import com.soc.back.application.port.out.reporte.BuscarReporteByUserPort;
import com.soc.back.application.port.out.reporte.CrearReportePort;
import com.soc.back.application.port.out.usuario.BuscarIdUsuarioPort;
import com.soc.back.application.port.out.usuario.CrearUsuarioPort;
import com.soc.back.common.enums.ExtensionEnum;
import com.soc.back.domain.Reporte;
import com.soc.back.domain.Usuario;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Log4j2
public class ReporteService implements ReportePort {

    @Autowired
    private CrearReportePort crearReportePort;

    @Autowired
    private BuscarReporteByUserPort buscarReporteByUserPort;

    @Autowired
    private BuscarIdUsuarioPort buscarIdUsuarioPort;

    @Override
    public void crearReporte(ReporteCommand comando) {
        Reporte reporte = new Reporte();
        reporte.setCodigoReporte(generarCodigoReporte());
        reporte.setUsuarioCreacion(comando.getUsuarioCreacion());
        String textoLimpio = quitarTildesComasPuntos(comando.getContenido());
        reporte.setTipo(detectarTipo(textoLimpio));
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fechaFormateada = LocalDateTime.now().format(formato);
        UsuarioCommand usuario = buscarIdUsuarioPort.buscarIdUsuarioPort(comando.getUsuarioCreacion());
        log.error(usuario);
        comando.setFechaCreacion(LocalDateTime.parse(fechaFormateada, formato));
        String nombreArchivo = "Reporte"+ "-" + reporte.getCodigoReporte();
        StringBuffer txt = new StringBuffer(comando.getTitulo());
        txt
                .append("-")
                .append(fechaFormateada)
                .append("\n")
                .append("Tipo: ").append(reporte.getTipo())
                .append("\n")
                .append(comando.getContenido())
                .append("\n")
                .append(usuario.getNombre()).append(" ").append(usuario.getApellido());
        try{
            File archivo = new File(nombreArchivo);
            FileWriter writer = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(txt.toString());
            bw.flush();
            writer.close();
            byte[] archivoTxt = Files.readAllBytes(Paths.get(archivo.getAbsolutePath()));
            byte[] contenido = txt.toString().getBytes(StandardCharsets.UTF_8);
            String base64 = Base64.getEncoder().encodeToString(archivoTxt);
            String contenidoBase64 = Base64.getEncoder().encodeToString(contenido);
            reporte.setFechaCreacion(comando.getFechaCreacion());
            reporte.setNombreReporte(nombreArchivo);
            reporte.setExtension(ExtensionEnum.TXT);
            reporte.setArchivo(base64);
            reporte.setContenido(contenidoBase64);
            archivo.delete();
            crearReportePort.crearReporte(reporte);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
     }

    @Override
    public List<ReporteCommand> buscarReporteByUser(ReporteCommand command) {
        return buscarReporteByUserPort.buscarReporteByUser(command.getUsuarioCreacion());
    }


    private String generarCodigoReporte(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0,5);
    }

    private String detectarTipo(String contenido){
        Map<String, List<String>> parametros = new HashMap<>();
        parametros.put("phishing", new ArrayList<String>(Arrays.asList("correo", "suplantacion", "link", "engañoso", "desconocido", "sms", "mensaje texto", "paginas", "falsas", "redireccion", "falsificacion")));
        parametros.put("keylogger", new ArrayList<String>(Arrays.asList("credenciales", "contraseñas", "acceso", "apps", "aplicaciones", "aplicacion", "mensaje texto", "registro", "captura", "espionaje", "vigilado", "vigilada", "pantalla")));
        parametros.put("ransomware", new ArrayList<String>(Arrays.asList("cifrado", "rescate", "pago", "bitcoins", "moneda", "extorsion", "miedo", "amenaza", "desencriptacion", "cifrar", "pagar", "tiempo", "pantalla", "bloqueo")));
        parametros.put("spyware", new ArrayList<String>(Arrays.asList("vigilancia", "monitoreo", "registro", "sospechoso", "privacidad", "software", "apps", "no deseado", "no instale", "lento", "filtracion", "datos", "personales")));
        parametros.put("Adware", new ArrayList<String>(Arrays.asList("anuncios", "molesto", "lento", "aplicacion", "aplicaciones", "aplicacion", "extension", "navegador", "no deja trabajar", "publicidad", "monitoreo", "ven")));
        parametros.put("RAT", new ArrayList<String>(Arrays.asList("control", "externo", "remoto", "capturas", "pantalla", "apps", "instaladas", "extraño", "aplicacion", "aplicaciones", "vigilado", "manipulacion", "movimiento", "terceros")));
        parametros.put("MitM", new ArrayList<String>(Arrays.asList("red", "lenta", "internet", "control", "modificacion", "autenticacion", "fallido", "bloqueos", "lento", "cargas", "lentas", "vigilada", "reedireccion")));

        StringBuilder tipo = new StringBuilder();

        parametros.keySet().forEach((k) -> {

            int cantidad = 0;
            for (String palabra:contenido.split(" ")){
                    for(String palabraTipos :parametros.get(k)){
                        if(palabra.equals(palabraTipos)){
                            cantidad++;
                        }
                    }
            }
            if(cantidad > 0){
                tipo.append(k).append(",");
            }
        });
        String tipoString = tipo.toString();
        String tipoSalida = tipoString.length() > 1 ? tipoString.substring(0, tipoString.length() - 1) : "";
        return tipoSalida;
    }

    private String quitarTildesComasPuntos(String cuerpo){

        String textoSinTildes = Normalizer.normalize(cuerpo, Normalizer.Form.NFD);
        textoSinTildes = textoSinTildes.replaceAll("[^\\p{ASCII}]", "");

        String textoSinComasPuntos = textoSinTildes.replaceAll("[,\\.]", "");

        return textoSinComasPuntos;

    }

}
