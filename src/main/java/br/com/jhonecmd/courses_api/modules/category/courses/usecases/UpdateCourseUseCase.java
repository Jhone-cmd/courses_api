package br.com.jhonecmd.courses_api.modules.category.courses.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CourseNotFound;
import br.com.jhonecmd.courses_api.modules.category.courses.dto.CourseResponseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.dto.UpdateCourseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;
import br.com.jhonecmd.courses_api.modules.category.courses.utils.CourseMapper;

@Service
public class UpdateCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    public CourseResponseDTO execute(String id, UpdateCourseDTO updateCourseDTO) {

        CourseEntity course = this.courseRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CourseNotFound());

        courseMapper.updateEntityFromDto(updateCourseDTO, course);

        this.courseRepository.save(course);

        return CourseResponseDTO.builder().id(course.getId()).name(course.getName())
                .description(course.getDescription()).categoryName(course.getCategoryEntity().getName())
                .active(course.getActive()).build();
    }
}
