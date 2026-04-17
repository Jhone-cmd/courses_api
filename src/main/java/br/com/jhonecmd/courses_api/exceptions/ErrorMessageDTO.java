package br.com.jhonecmd.courses_api.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageDTO {

    @Schema(example = "The name is required.")
    private String error;

    @Schema(example = "name")
    private String field;
}
