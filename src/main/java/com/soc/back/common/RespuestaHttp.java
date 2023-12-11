package com.soc.back.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class RespuestaHttp implements Serializable {

    private String estatus;

    private String codigo;

    private Object mensaje;

}
