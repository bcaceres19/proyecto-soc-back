package com.soc.back.adapter.in.web;

import com.soc.back.adapter.out.persistence.repository.AdminRepository;
import com.soc.back.application.port.in.AdminPort;
import com.soc.back.application.port.in.UsuarioPort;
import com.soc.back.application.port.in.command.AdminCommand;
import com.soc.back.application.port.in.command.AdminUserCommand;
import com.soc.back.common.RespuestaHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/user/")
public class AdminUserController extends GeneralController{

    @Autowired
    private AdminPort adminPort;

    @Autowired
    private UsuarioPort usuarioPort;

    @PostMapping(path = "/buscarExistencia")
    @ResponseBody
    public RespuestaHttp buscarExistAdminUser(@RequestBody AdminUserCommand command){
        RespuestaHttp respuestaHttp;
        try{
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp, null, HttpStatus.OK);

            boolean emailAdmin = adminPort.buscarEmailAdmin(command.getEmail());
            boolean emailUser = usuarioPort.buscarEmailUsuario(command.getEmail());

            if(emailAdmin){
                AdminUserCommand admin = adminPort.buscarExistAdmin(command);
                if(admin != null){
                    respuestaHttp.setMensaje(admin);
                }else{
                    respuestaFinalHttp(respuestaHttp, "La contraseña ingresada es incorrecta", HttpStatus.BAD_REQUEST);
                }
            }else if(emailUser){
                AdminUserCommand usuario = usuarioPort.buscarExistUser(command);
                if(usuario != null){
                    respuestaHttp.setMensaje(usuario);
                }else{
                    respuestaFinalHttp(respuestaHttp, "La contraseña ingresada es incorrecta", HttpStatus.BAD_REQUEST);
                }
            }else{
                respuestaFinalHttp(respuestaHttp, "No existe ningun usuario registrado con este correo", HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            respuestaHttp =  new RespuestaHttp();
            respuestaFinalHttp(respuestaHttp, "Hubo un fallo en la creacion del admin", HttpStatus.INTERNAL_SERVER_ERROR);
            System.err.println(e.getMessage());
        }
        return respuestaHttp;
    }



}
