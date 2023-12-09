package com.soc.back.adapter.in.web;

import com.soc.back.application.port.in.AdminPort;
import com.soc.back.application.port.in.ReportePort;
import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.in.command.ReporteCommand;
import com.soc.back.common.RespuestaHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reporte/")
public class ReporteController {

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
            respuestaHttp.setMenaje("Se creo correctamente el reporte");
        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            respuestaHttp.setEstatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
            respuestaHttp.setMenaje("Hubo un fallo en la creacion del reporte");
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

}
