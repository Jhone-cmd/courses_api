package br.com.jhonecmd.courses_api.modules.users.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.courses_api.exceptions.ErrorMessageDTO;
import br.com.jhonecmd.courses_api.exceptions.UserAlreadyExists;
import br.com.jhonecmd.courses_api.modules.users.dto.ChangePasswordUserDTO;
import br.com.jhonecmd.courses_api.modules.users.dto.CreateUserDTO;
import br.com.jhonecmd.courses_api.modules.users.dto.UpdateUserDTO;
import br.com.jhonecmd.courses_api.modules.users.dto.UserResponseDTO;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.usecases.ChangePasswordUseCase;
import br.com.jhonecmd.courses_api.modules.users.usecases.CreateUserUseCase;
import br.com.jhonecmd.courses_api.modules.users.usecases.DeleteUserUseCase;
import br.com.jhonecmd.courses_api.modules.users.usecases.FetchAllUserUseCase;
import br.com.jhonecmd.courses_api.modules.users.usecases.GetByUserUseCase;
import br.com.jhonecmd.courses_api.modules.users.usecases.UpdateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Routes intended for users.")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    private final GetByUserUseCase getByUserUseCase;

    private final FetchAllUserUseCase fetchAllUserUseCase;

    private final UpdateUserUseCase updateUserUseCase;

    private final ChangePasswordUseCase changePasswordUseCase;

    private final DeleteUserUseCase deleteUserUseCase;

    UserController(CreateUserUseCase createUserUseCase, GetByUserUseCase getByUserUseCase,
            ChangePasswordUseCase changePasswordUseCase, UpdateUserUseCase updateUserUseCase,
            DeleteUserUseCase deleteUserUseCase, FetchAllUserUseCase fetchAllUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getByUserUseCase = getByUserUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.fetchAllUserUseCase = fetchAllUserUseCase;
    }

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

    @GetMapping("/profile")
    @Operation(summary = "View user profile.", description = "This route is designed to view profile of user.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = UserResponseDTO.class))
            })
    })
    public ResponseEntity<Object> profile(HttpServletRequest request) {
        try {

            var userId = request.getAttribute("userId");
            var result = this.getByUserUseCase.execute(userId.toString());
            return ResponseEntity.ok(result);

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

            var users = this.fetchAllUserUseCase.execute();
            return ResponseEntity.status(HttpStatus.OK).body(users);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update data a specific user.", description = "This route is designed to update data a specific user.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Data updated successfully", content = @Content(schema = @Schema(implementation = UpdateUserDTO.class)))
    })
    public ResponseEntity<Object> update(@PathVariable() String id,
            @RequestBody UpdateUserDTO updateUserDTO) {
        try {

            var user = this.updateUserUseCase.execute(id, updateUserDTO);
            return ResponseEntity.ok(user);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/change-password")
    @Operation(summary = "Update password a specific user.", description = "This route is designed to update password a specific user.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Password updated successfully", content = @Content(schema = @Schema(implementation = ChangePasswordUserDTO.class)))
    })
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordUserDTO changePasswordUserDTO) {
        try {

            this.changePasswordUseCase.execute(changePasswordUserDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RECTOR')")
    @Operation(summary = "Delete a specific user.", description = "This route is designed to delete a specific user.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User successfully deleted.")
    })
    public ResponseEntity<Object> delete(@PathVariable() String id) {
        try {

            this.deleteUserUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
