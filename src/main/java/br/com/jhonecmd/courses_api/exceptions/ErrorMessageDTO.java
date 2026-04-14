package br.com.jhonecmd.courses_api.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageDTO {

    @Schema(example = "The email field is invalid.")
    private String error;

    @Schema(example = "email")
    private String field;
}
