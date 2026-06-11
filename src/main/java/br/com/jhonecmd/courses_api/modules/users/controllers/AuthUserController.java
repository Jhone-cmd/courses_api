package br.com.jhonecmd.courses_api.modules.users.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.courses_api.modules.users.dto.AuthUserDTO;
import br.com.jhonecmd.courses_api.modules.users.dto.AuthUserResponseDTO;
import br.com.jhonecmd.courses_api.modules.users.usecases.AuthenticateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Routes intended for users.")
public class AuthUserController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    AuthUserController(AuthenticateUserUseCase authenticateUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    @PostMapping("/auth")
    @Operation(summary = "User authentication.", description = "This route is designed to user authentication.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token generated successfully.", content = {
                    @Content(schema = @Schema(implementation = AuthUserResponseDTO.class))
            }),

            @ApiResponse(responseCode = "401", description = "Invalid Credentials", content = {
                    @Content(schema = @Schema(implementation = String.class, example = "Invalid Credentials."))
            })
    })
    public ResponseEntity<Object> session(@RequestBody AuthUserDTO authUserDTO) {
        try {

            var result = this.authenticateUserUseCase.execute(authUserDTO);

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
