package com.soc.back.adapter.in.web;


import com.soc.back.application.port.in.AdminPort;
import com.soc.back.application.port.in.UsuarioPort;
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
public class AdminController extends GeneralController{

    @Autowired
    private AdminPort adminPort;

    @Autowired
    private UsuarioPort usuarioPort;

    @PostMapping(path = "/crear")
    @ResponseBody
    public RespuestaHttp crearAdmin(@RequestBody AdminCommand command){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            boolean emailExists = usuarioPort.buscarEmailUsuario(command.getEmail());
            boolean adminExists = adminPort.buscarEmailAdmin(command.getEmail());
            if(emailExists || adminExists){
                respuestaFinalHttp(respuestaHttp, "El email ingresado ya es de alguien mas, porfavor, ingresa uno nuevo", HttpStatus.BAD_REQUEST);
            }else{
                adminPort.crearAdmin(command);
                respuestaFinalHttp(respuestaHttp, "Se creo correctamente el admin", HttpStatus.CREATED);
            }
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

    @GetMapping("/allAdmins")
    @ResponseBody
    public RespuestaHttp allAdmins(){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp, adminPort.traerAllAdmins(), HttpStatus.OK);
        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp, "Hubo un fallo en la creacion del usuario", HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

    @GetMapping("/allReportsAdmin")
    @ResponseBody
    public RespuestaHttp reportesAdmin(@RequestParam("idAdmin") Long idAdmin){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp, adminPort.reportesAdmin(idAdmin), HttpStatus.OK);
        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp, "Hubo un fallo en la creacion del usuario", HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }

}
