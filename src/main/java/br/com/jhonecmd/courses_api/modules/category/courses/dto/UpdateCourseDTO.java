package br.com.jhonecmd.courses_api.modules.category.courses.dto;

import lombok.Data;

@Data
public class UpdateCourseDTO {
    private String name;
    private String description;
    private String categoryName;
}
