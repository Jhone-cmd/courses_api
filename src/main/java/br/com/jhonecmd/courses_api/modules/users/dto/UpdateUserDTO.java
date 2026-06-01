package br.com.jhonecmd.courses_api.modules.users.dto;

import org.hibernate.validator.constraints.Length;

import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {

    @Schema(example = "Stefanie")
    private String name;

    @Schema(example = "stefanie@yahoo.com")
    private String email;

    @Schema(example = "Director")
    private Position position;
}
