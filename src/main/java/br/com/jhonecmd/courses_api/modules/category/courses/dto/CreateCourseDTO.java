package br.com.jhonecmd.courses_api.modules.category.courses.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCourseDTO {

    @NotBlank(message = "Name is required.")
    private String name;
    private String description;
    private Boolean active;
    @NotBlank(message = "teacher is required.")
    private String teacherName;
    private UUID categoryId;
}
