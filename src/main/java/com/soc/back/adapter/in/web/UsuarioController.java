package com.soc.back.adapter.in.web;

import com.soc.back.application.port.in.AdminPort;
import com.soc.back.application.port.in.UsuarioPort;
import com.soc.back.application.port.in.command.ReporteCommand;
import com.soc.back.application.port.in.command.UsuarioCommand;
import com.soc.back.common.RespuestaHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuario/")
public class UsuarioController extends GeneralController{

    @Autowired
    private UsuarioPort usuarioPort;

    @Autowired
    private AdminPort adminPort;

    @PostMapping(path = "/crear")
    @ResponseBody
    public RespuestaHttp crearUsuario(@RequestBody UsuarioCommand command){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            boolean emailExists = usuarioPort.buscarEmailUsuario(command.getEmail());
            boolean adminExists = adminPort.buscarEmailAdmin(command.getEmail());
            if(emailExists || adminExists){
                respuestaFinalHttp(respuestaHttp, "El email ingresado ya es de alguien mas, porfavor, ingresa uno nuevo", HttpStatus.BAD_REQUEST);
            }else{
                usuarioPort.crearUsuario(command);
                respuestaFinalHttp(respuestaHttp, "Se creo correctamente el usuario", HttpStatus.CREATED);
            }
        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp, "Hubo un fallo en la creacion del usuario", HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

    @GetMapping("/allUsuarios")
    @ResponseBody
    public RespuestaHttp allUsuarios(){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp, usuarioPort.traerAllUsuarios(), HttpStatus.OK);
        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp, "Hubo un fallo en la creacion del usuario", HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }
}
