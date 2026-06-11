package br.com.jhonecmd.courses_api.modules.categories.courses.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CourseNotFound;
import br.com.jhonecmd.courses_api.modules.categories.courses.dto.CourseResponseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.repositories.CourseRepository;

@Service
public class GetByCourseUseCase {

    private final CourseRepository courseRepository;

    GetByCourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseResponseDTO execute(String id) {

        var course = this.courseRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CourseNotFound());

        return CourseResponseDTO.builder().id(course.getId()).name(course.getName())
                .description(course.getDescription()).categoryName(course.getCategoryEntity().getName())
                .teacherName(course.getUserEntity().getName())
                .active(course.getActive()).build();
    }
}
