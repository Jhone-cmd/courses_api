package br.com.jhonecmd.courses_api.modules.category.courses.utils;

import org.mapstruct.*;

import br.com.jhonecmd.courses_api.modules.category.courses.dto.CourseResponseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.dto.UpdateCourseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateCourseDTO updateCourseDTO, @MappingTarget CourseEntity courseEntity);
}
