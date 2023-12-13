package com.soc.back.adapter.in.web;

import com.soc.back.application.port.in.AdminPort;
import com.soc.back.application.port.in.ReportePort;
import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.in.command.ReporteCommand;
import com.soc.back.common.RespuestaHttp;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/reporte/")
@Log4j2
public class ReporteController extends GeneralController{

    @Autowired
    private ReportePort reportePort;

    @PostMapping(path = "/crear")
    @ResponseBody
    public RespuestaHttp crearReporte(@RequestBody ReporteCommand command){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            reportePort.crearReporte(command);
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.CREATED.value()));
            respuestaHttp.setEstatus(HttpStatus.CREATED.name());
            respuestaHttp.setMensaje("Se creo correctamente el reporte");
        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            respuestaHttp.setEstatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
            respuestaHttp.setMensaje("Hubo un fallo en la creacion del reporte");
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

    @PostMapping(path = "/actualizar")
    @ResponseBody
    public RespuestaHttp actualizarReporte(@RequestBody ReporteCommand command){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            reportePort.actualizarReporte(command);
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.CREATED.value()));
            respuestaHttp.setEstatus(HttpStatus.CREATED.name());
            respuestaHttp.setMensaje("Se actualizo correctamente el reporte");
        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            respuestaHttp.setEstatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
            respuestaHttp.setMensaje("Hubo un fallo en la actualizacion del reporte");
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

    @PostMapping(path = "/reportesUsuario")
    @ResponseBody
    public RespuestaHttp traerReportesUsuario(@RequestBody ReporteCommand command){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp,reportePort.buscarReporteByUser(command), HttpStatus.OK);

        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp,"Hubo un fallo en la traida de reportes", HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

    @GetMapping(path = "/buscarReporteFecha")
    @ResponseBody
    public RespuestaHttp buscarReporteFecha(@RequestParam("fecha") String fecha, @RequestParam(value = "idAdmin", required = false) Long idAdmin, @RequestParam(value = "idUsuario", required = false) Long idUsuario){
        RespuestaHttp respuestaHttp;
        try{

            respuestaHttp =  new RespuestaHttp();
            LocalDateTime fechaEnviada = LocalDateTime.parse(fecha+".000000001");
            respuestaFinalHttp(respuestaHttp,reportePort.buscarReporteByFecha(fechaEnviada, idAdmin, idUsuario), HttpStatus.OK);

        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp,"Hubo un fallo en la traida de reportes", HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

    @GetMapping(path = "/buscarReporteCodigo")
    @ResponseBody
    public RespuestaHttp buscarReporteCodigo(@RequestParam("codigo") String codigo, @RequestParam(value = "idAdmin", required = false) Long idAdmin, @RequestParam(value = "idUsuario", required = false) Long idUsuario){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp,reportePort.buscarReporteByCodigo(codigo, idAdmin, idUsuario), HttpStatus.OK);

        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp,"Hubo un fallo en la traida de reportes", HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminar")
    @ResponseBody
    public RespuestaHttp eliminarReporte(@RequestParam("codigo") String codigo){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            reportePort.eliminarReporte(codigo);
            respuestaFinalHttp(respuestaHttp,"Se eliminaron correctamente el reporte", HttpStatus.OK);

        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp,"Hubo un fallo en la eliminar el reporte", HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

    @PostMapping("/confirmar")
    @ResponseBody
    public RespuestaHttp confirmarReporte(@RequestParam("codigo") String codigo){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            reportePort.aceptarReporte(codigo);
            respuestaFinalHttp(respuestaHttp,"Se acepto correctamente el reporte", HttpStatus.OK);

        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp,"Hubo un fallo en la aceptacion el reporte", HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }
}
