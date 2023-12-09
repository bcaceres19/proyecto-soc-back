package com.soc.back.adapter.in.web;

import com.soc.back.application.port.in.UsuarioPort;
import com.soc.back.application.port.in.command.ReporteCommand;
import com.soc.back.application.port.in.command.UsuarioCommand;
import com.soc.back.common.RespuestaHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuario/")
public class UsuarioController {

    @Autowired
    private UsuarioPort usuarioPort;

    @PostMapping(path = "/crear")
    @ResponseBody
    public RespuestaHttp crearReporte(@RequestBody UsuarioCommand command){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            usuarioPort.crearUsuario(command);
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.CREATED.value()));
            respuestaHttp.setEstatus(HttpStatus.CREATED.name());
            respuestaHttp.setMenaje("Se creo correctamente el usuario");
        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaHttp.setCodigo(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            respuestaHttp.setEstatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
            respuestaHttp.setMenaje("Hubo un fallo en la creacion del usuario");
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

}
