package com.soc.back.adapter.in.web;


import com.soc.back.application.port.in.AdminPort;
import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.common.RespuestaHttp;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/")
public class AdminController {

    @Autowired
    private AdminPort adminPort;

    @PostMapping(path = "/crear")
    @ResponseBody
    public RespuestaHttp crearAdmin(@RequestBody AdminCommand command){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            adminPort.crearAdmin(command);
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.CREATED.value()));
            respuestaHttp.setEstatus(HttpStatus.CREATED.name());
            respuestaHttp.setMensaje("Se creo correctamente el admin");
        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            respuestaHttp.setEstatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
            respuestaHttp.setMensaje("Hubo un fallo en la creacion del admin");
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

    @PutMapping(path = "/actualizar")
    @ResponseBody
    public RespuestaHttp actualizarAdmin(@RequestBody AdminCommand command){
        RespuestaHttp respuestaHttp =  new RespuestaHttp();
        try{
            adminPort.actualizarAdmin(command);
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.OK.value()));
            respuestaHttp.setEstatus(HttpStatus.OK.name());
            respuestaHttp.setMensaje("Se actualizo correctamente el admin");
        }catch (Exception e){
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            respuestaHttp.setEstatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
            respuestaHttp.setMensaje("Hubo un fallo en la actualizacion del admin");
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }
}
