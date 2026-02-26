package br.com.jhonecmd.courses_api.modules.users.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.jhonecmd.courses_api.modules.users.dto.CreateUserDTO;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.usecases.CreateUserUseCase;
import br.com.jhonecmd.courses_api.modules.users.usecases.FetchAllUserUseCase;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FetchAllUserUseCase fetchUserUseCase;

    @PostMapping("")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {

            var userEntity = UserEntity.builder().name(createUserDTO.getName()).email(createUserDTO.getEmail())
                    .password(createUserDTO.getPassword()).position(UserEntity.fromValue(createUserDTO.getPosition()))
                    .build();

            var password = this.passwordEncoder.encode(createUserDTO.getPassword());

            userEntity.setPassword(password);

            this.createUserUseCase.execute(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> fetchAllUsers() {
        try {

            var users = this.fetchUserUseCase.execute();
            return ResponseEntity.status(HttpStatus.OK).body(users);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
