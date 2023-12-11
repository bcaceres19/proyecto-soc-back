package com.soc.back.adapter.in.web;

import com.soc.back.common.RespuestaHttp;
import org.springframework.http.HttpStatus;

public class GeneralController {

    protected void respuestaFinalHttp(RespuestaHttp respuestaHttp, Object mensaje, HttpStatus status){
        respuestaHttp.setCodigo(String.valueOf(status.value()));
        respuestaHttp.setEstatus(status.name());
        respuestaHttp.setMensaje(mensaje);
    }

}
