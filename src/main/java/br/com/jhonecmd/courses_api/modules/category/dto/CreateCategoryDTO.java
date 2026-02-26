package br.com.jhonecmd.courses_api.modules.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryDTO {
    @NotBlank(message = "Name is required.")
    private String name;
}
