package br.com.jhonecmd.courses_api.modules.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryDTO {

    @Schema(example = "Tecnologia")
    @NotBlank(message = "Name is required.")
    private String name;
}
