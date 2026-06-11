package br.com.jhonecmd.courses_api.modules.categories.courses.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CourseNotFound;
import br.com.jhonecmd.courses_api.modules.categories.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.categories.courses.repositories.CourseRepository;

@Service
public class ChangeStatusCourseUseCase {

    private final CourseRepository courseRepository;

    ChangeStatusCourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void execute(String id, Boolean active) {

        CourseEntity course = this.courseRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CourseNotFound());

        course.setActive(active);

        this.courseRepository.save(course);

        return;
    }
}
