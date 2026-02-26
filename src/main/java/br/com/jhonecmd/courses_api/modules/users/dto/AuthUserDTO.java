package br.com.jhonecmd.courses_api.modules.users.dto;

import lombok.Data;

@Data
public class AuthUserDTO {
    private String email;
    private String password;
}
