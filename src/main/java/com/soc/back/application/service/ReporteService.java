package com.soc.back.application.service;

import com.soc.back.adapter.out.persistence.mapper.AdminMapper;
import com.soc.back.application.port.in.ReportePort;
import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.in.command.AdminReporteCommand;
import com.soc.back.application.port.in.command.ReporteCommand;
import com.soc.back.application.port.in.command.UsuarioCommand;
import com.soc.back.application.port.out.admin.BuscarAdminDispoPort;
import com.soc.back.application.port.out.admin.ReportesAdminPort;
import com.soc.back.application.port.out.adminreporte.BuscarConfirmacionReportePort;
import com.soc.back.application.port.out.adminreporte.CrearAdminReportePort;
import com.soc.back.application.port.out.adminreporte.EliminarAdminReportePort;
import com.soc.back.application.port.out.reporte.*;
import com.soc.back.application.port.out.usuario.BuscarIdUsuarioPort;
import com.soc.back.common.enums.ExtensionEnum;
import com.soc.back.domain.Reporte;
import jakarta.transaction.Transactional;
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
import java.util.stream.Collectors;

@Service
@Log4j2
public class ReporteService implements ReportePort {

    @Autowired
    private CrearReportePort crearReportePort;

    @Autowired
    private BuscarReporteByUserPort buscarReporteByUserPort;

    @Autowired
    private BuscarIdUsuarioPort buscarIdUsuarioPort;

    @Autowired
    private BuscarReporteByCodigoAdminPort buscarReporteByCodigoAdminPort;

    @Autowired
    private BuscarReporteByFechaAdminPort buscarReporteByFechaAdminPort;

    @Autowired
    private BuscarAdminDispoPort buscarAdminDispoPort;

    @Autowired
    private CrearAdminReportePort crearAdminReportePort;

    @Autowired
    private ActualizarReportePort actualizarReportePort;

    @Autowired
    private ReportesAdminPort reportesAdminPort;

    @Autowired
    private BuscarReporteByCodigoUserPort buscarReporteByCodigoUserPort;

    @Autowired
    private BuscarReporteByFechaUserPort buscarReporteByFechaUserPort;

    @Autowired
    private EliminarReportePort eliminarReportePort;

    @Autowired
    private EliminarAdminReportePort eliminarAdminReportePort;

    @Autowired
    private AceptarReportePort aceptarReportePort;

    @Autowired
    private BuscarConfirmacionReportePort buscarConfirmacionReportePort;

    @Override
    public void crearReporte(ReporteCommand comando) {
        Reporte reporte = new Reporte();
        reporte.setCodigoReporte(generarCodigoReporte());
        reporte.setUsuarioCreacion(comando.getUsuarioCreacion());
        reporte.setTituloReporte(comando.getTituloReporte());
        reporte.setCuerpoGeneral(comando.getCuerpoGeneral());
        String textoLimpio = quitarTildesComasPuntos(comando.getCuerpoGeneral());
        reporte.setTipo(detectarTipo(textoLimpio));
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fechaFormateada = LocalDateTime.now().format(formato);
        UsuarioCommand usuario = buscarIdUsuarioPort.buscarIdUsuarioPort(comando.getUsuarioCreacion());
        comando.setFechaCreacion(LocalDateTime.parse(fechaFormateada, formato));
        String nombreArchivo = "Reporte"+ "-" + reporte.getCodigoReporte();
        StringBuffer txt = new StringBuffer(comando.getTituloReporte());
        txt
                .append("-")
                .append(fechaFormateada)
                .append("\n")
                .append("Tipo: ").append(reporte.getTipo())
                .append("\n")
                .append(comando.getCuerpoGeneral())
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
            AdminReporteCommand adminReporteCommand = new AdminReporteCommand();
            adminReporteCommand.setCodigoReporte(reporte.getCodigoReporte());
            adminReporteCommand.setAdmin(AdminMapper.INSTANCE.commadnToEntity(adminAsignado()));
            crearAdminReportePort.crearAdminReporte(adminReporteCommand);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
     }

    @Override
    public List<ReporteCommand> buscarReporteByUser(ReporteCommand command) {
        List<ReporteCommand> reportes = new ArrayList<>();
        return buscarReporteByUserPort.buscarReporteByUser(command.getUsuarioCreacion());
    }

    @Override
    public List<ReporteCommand> buscarReporteByCodigo(String codigo, Long idAdmin, Long idUsuario) {
        List<ReporteCommand> reportes = new ArrayList<>();
        if(idAdmin != null){
            List<Object[]> reportesAdmin = reportesAdminPort.reportesAdmin(idAdmin);
            reportes = this.buscarReporteByCodigoAdminPort.buscarReporteByCodigo(codigo);
            // Obtener los códigos de reportesAdmin
            Set<String> codigosReportesAdmin = reportesAdmin.stream()
                    .map(report -> (String) report[0])  // Asumiendo que el código está en la posición 0 del array
                    .collect(Collectors.toSet());
            reportes = reportes.stream()
                    .filter(reporte -> codigosReportesAdmin.contains(reporte.getCodigoReporte()))
                    .collect(Collectors.toList());
        }else if(idUsuario != null){
            for(ReporteCommand reporte : this.buscarReporteByCodigoUserPort.buscarReporteByCodigoUser(codigo,idUsuario)){
                log.error(buscarConfirmacionReportePort.buscarConfirmacionReporte(reporte.getCodigoReporte()));
                if(buscarConfirmacionReportePort.buscarConfirmacionReporte(reporte.getCodigoReporte())){
                    reportes.add(reporte);
                }
            }
        }else{
            reportes =  this.buscarReporteByCodigoAdminPort.buscarReporteByCodigo(codigo);
        }
        return reportes;
    }

    @Override
    public List<ReporteCommand> buscarReporteByFecha(LocalDateTime fecha, Long idAdmin, Long idUsuario) {
        List<ReporteCommand> reportes = new ArrayList<>();
        if(idAdmin != null){
            List<Object[]> reportesAdmin = reportesAdminPort.reportesAdmin(idAdmin);
            reportes = this.buscarReporteByFechaAdminPort.buscarReporteByFecha(fecha);
            // Obtener los códigos de reportesAdmin
            Set<String> codigosReportesAdmin = reportesAdmin.stream()
                    .map(report -> (String) report[0])  // Asumiendo que el código está en la posición 0 del array
                    .collect(Collectors.toSet());
            reportes = reportes.stream()
                    .filter(reporte -> codigosReportesAdmin.contains(reporte.getCodigoReporte()))
                    .collect(Collectors.toList());
        }else {
            for(ReporteCommand reporte : this.buscarReporteByFechaUserPort.buscarReporteByFecha(fecha,idUsuario)){
                if(buscarConfirmacionReportePort.buscarConfirmacionReporte(reporte.getCodigoReporte())){
                    reportes.add(reporte);
                }
            }
        }

        return reportes;
    }

    @Override
    public void actualizarReporte(ReporteCommand command) {
        String textoLimpio = quitarTildesComasPuntos(command.getCuerpoGeneral());
        command.setTipo(detectarTipo(textoLimpio));
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fechaFormateada = LocalDateTime.now().format(formato);
        UsuarioCommand usuario = buscarIdUsuarioPort.buscarIdUsuarioPort(command.getUsuarioCreacion());
        String nombreArchivo = "Reporte"+ "-" + command.getCodigoReporte();
        StringBuffer txt = new StringBuffer(command.getTituloReporte());
        txt
                .append("-")
                .append(fechaFormateada)
                .append("\n")
                .append("Tipo: ").append(command.getTipo())
                .append("\n")
                .append(command.getCuerpoGeneral())
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
            command.setFechaCreacion(command.getFechaCreacion());
            command.setNombreReporte(nombreArchivo);
            command.setExtension(ExtensionEnum.TXT);
            command.setArchivo(base64);
            command.setContenido(contenidoBase64);
            archivo.delete();
            this.actualizarReportePort.actualizarReporte(command);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void eliminarReporte(String codigoReporte) {
        eliminarReportePort.eliminarReporte(codigoReporte);
        eliminarAdminReportePort.eliminarAdminReporte(codigoReporte);
    }

    @Override
    public void aceptarReporte(String codigoReporte) {
        aceptarReportePort.aceptarReporte(codigoReporte);
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


    private AdminCommand adminAsignado(){
        AdminCommand admin = new AdminCommand();
        admin.setId(Long.valueOf(buscarAdminDispoPort.buscarAdminDispo().get(0)[0].toString()));
        return admin;
    }

}
