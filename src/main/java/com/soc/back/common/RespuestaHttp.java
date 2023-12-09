package com.soc.back.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class RespuestaHttp implements Serializable {

    private String estatus;

    private String codigo;

    private String menaje;


    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMenaje() {
        return menaje;
    }

    public void setMenaje(String menaje) {
        this.menaje = menaje;
    }
}
