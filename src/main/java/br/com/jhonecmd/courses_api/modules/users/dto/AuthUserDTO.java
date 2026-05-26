package br.com.jhonecmd.courses_api.modules.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDTO {
    @Schema(example = "stefanie@yahoo.com")
    private String email;

    @Schema(example = "stefanie!4568")
    private String password;
}
