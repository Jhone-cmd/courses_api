package br.com.jhonecmd.courses_api.modules.category.courses.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.modules.category.courses.dto.CourseResponseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class GetByCourseUseCase {
    @Autowired
    private CourseRepository courseRepository;

    public CourseResponseDTO execute(String id) {
        var course = this.courseRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + id));

        return CourseResponseDTO.builder().id(course.getId()).name(course.getName())
                .description(course.getDescription()).categoryName(course.getCategoryEntity().getName())
                .active(course.getActive()).build();
    }
}
