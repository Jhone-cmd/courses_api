package br.com.jhonecmd.courses_api.modules.category.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseDTO {
    private String name;
    private String description;
    private String categoryName;
    private String teacherName;
}
