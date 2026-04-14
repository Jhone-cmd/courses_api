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
    @Schema(example = "johndoe@email.com")
    private String email;

    @Schema(example = "123457890")
    private String password;
}
