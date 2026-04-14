package br.com.jhonecmd.courses_api.modules.users.dto;

import java.time.LocalDateTime;
import java.util.UUID;

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
public class UserResponseDTO {

    private UUID id;
    @Schema(example = "Stefanie")
    private String name;

    @Schema(example = "stefanie@yahoo.com")
    private String email;

    @Schema(example = "Director")
    private Position position;

    private LocalDateTime createAt;
}
