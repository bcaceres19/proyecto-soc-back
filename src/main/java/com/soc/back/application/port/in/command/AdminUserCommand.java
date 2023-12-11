package com.soc.back.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserCommand {

    public AdminUserCommand(Long id, String email, boolean admin, boolean usuario) {
        this.id = id;
        this.email = email;
        this.admin = admin;
        this.usuario = usuario;
    }

    private Long id;

    private String email;

    private String password;

    private boolean admin;

    private boolean usuario;

}
