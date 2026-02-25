package br.com.jhonecmd.courses_api.modules.users.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserDTO {

    @NotBlank(message = "Name is required.")
    private String name;

    @Email(message = "The email field is invalid.")
    @NotBlank(message = "Email is required.")
    private String email;

    @Length(min = 8, max = 24, message = "The password length must be between 10 and 100 characters.")
    private String password;

    @NotBlank(message = "Position is required. The valid values are: 'rector, director, coordinator, professor, student'")
    private String position;
}
