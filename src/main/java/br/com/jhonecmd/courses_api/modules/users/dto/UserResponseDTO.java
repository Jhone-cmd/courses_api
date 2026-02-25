package br.com.jhonecmd.courses_api.modules.users.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
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
    private String name;
    private String email;
    private Position position;
    private LocalDateTime createAt;
}
