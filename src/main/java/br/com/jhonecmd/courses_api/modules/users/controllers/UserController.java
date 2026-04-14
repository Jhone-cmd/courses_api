package br.com.jhonecmd.courses_api.modules.users.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.jhonecmd.courses_api.exceptions.ErrorMessageDTO;
import br.com.jhonecmd.courses_api.exceptions.UserAlreadyExists;
import br.com.jhonecmd.courses_api.modules.users.dto.CreateUserDTO;
import br.com.jhonecmd.courses_api.modules.users.dto.UserResponseDTO;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.usecases.CreateUserUseCase;
import br.com.jhonecmd.courses_api.modules.users.usecases.FetchAllUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Routes intended for users.")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private FetchAllUserUseCase fetchUserUseCase;

    @PostMapping("")
    @Operation(summary = "Create a user.", description = "This route is designed to create a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),

            @ApiResponse(responseCode = "409", description = "User already exists.", content = {
                    @Content(schema = @Schema(implementation = UserAlreadyExists.class, example = "User already exists!"))
            }),

            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class))))
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {

            var userEntity = UserEntity.builder().name(createUserDTO.getName()).email(createUserDTO.getEmail())
                    .password(createUserDTO.getPassword()).position(UserEntity.fromValue(createUserDTO.getPosition()))
                    .build();

            this.createUserUseCase.execute(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (UserAlreadyExists ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR')")
    @Operation(summary = "View users.", description = "This route is designed to view users.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))))
    })
    public ResponseEntity<Object> fetchAllUsers() {
        try {

            var users = this.fetchUserUseCase.execute();
            return ResponseEntity.status(HttpStatus.OK).body(users);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
