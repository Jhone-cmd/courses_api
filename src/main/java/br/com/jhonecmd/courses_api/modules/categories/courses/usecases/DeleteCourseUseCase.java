package br.com.jhonecmd.courses_api.modules.categories.courses.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CourseNotFound;
import br.com.jhonecmd.courses_api.modules.categories.courses.repositories.CourseRepository;

@Service
public class DeleteCourseUseCase {

    private final CourseRepository courseRepository;

    DeleteCourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void execute(String courseId) {

        var course = this.courseRepository.findById(UUID.fromString(courseId))
                .orElseThrow(() -> new CourseNotFound());

        this.courseRepository.delete(course);

        return;
    }
}
