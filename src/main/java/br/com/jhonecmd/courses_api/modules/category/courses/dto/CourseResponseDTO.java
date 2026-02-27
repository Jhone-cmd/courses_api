package br.com.jhonecmd.courses_api.modules.category.courses.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private String categoryName;
    private Boolean active;

}
