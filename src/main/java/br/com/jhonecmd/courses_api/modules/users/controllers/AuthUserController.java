package br.com.jhonecmd.courses_api.modules.users.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.courses_api.modules.users.dto.AuthUserDTO;
import br.com.jhonecmd.courses_api.modules.users.usecases.AuthenticateUserUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class AuthUserController {

    @Autowired
    private AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> session(@RequestBody AuthUserDTO authUserDTO) {
        try {

            var result = this.authenticateUserUseCase.execute(authUserDTO);

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
