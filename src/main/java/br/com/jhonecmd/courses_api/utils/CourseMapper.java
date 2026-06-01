package br.com.jhonecmd.courses_api.utils;

import org.mapstruct.*;

import br.com.jhonecmd.courses_api.modules.categories.courses.dto.UpdateCourseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.entities.CourseEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper extends GenericMapper<UpdateCourseDTO, CourseEntity> {
}
