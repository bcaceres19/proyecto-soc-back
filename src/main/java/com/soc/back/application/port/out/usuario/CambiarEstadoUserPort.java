package com.soc.back.application.port.out.usuario;

public interface CambiarEstadoUserPort {

    void cambiarEstadoUser(boolean estado, Long idUser);

}
