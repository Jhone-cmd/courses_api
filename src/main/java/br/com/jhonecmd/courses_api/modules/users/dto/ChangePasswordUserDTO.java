package br.com.jhonecmd.courses_api.modules.users.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordUserDTO {

    @Schema(example = "stefanie@yahoo.com")
    @Email(message = "The email field is invalid.")
    @NotBlank(message = "Email is required.")
    private String email;

    @Schema(example = "stefanie!4568")
    @Length(min = 8, max = 100, message = "The password length must be between 10 and 100 characters.")
    private String password;
}
