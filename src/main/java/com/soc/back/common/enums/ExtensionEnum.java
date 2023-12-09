package com.soc.back.common.enums;

import lombok.Getter;

@Getter
public enum ExtensionEnum {

    TXT(".txt");

    private final String descripcion;

    ExtensionEnum(String descripcion){
        this.descripcion = descripcion;
    }

}
