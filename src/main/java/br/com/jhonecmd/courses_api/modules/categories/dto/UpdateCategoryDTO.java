package br.com.jhonecmd.courses_api.modules.categories.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryDTO {

    @Schema(example = "Tecnologia")
    private String name;
}
