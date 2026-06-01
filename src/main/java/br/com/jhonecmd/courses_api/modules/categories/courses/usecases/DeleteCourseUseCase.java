package br.com.jhonecmd.courses_api.modules.categories.courses.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CourseNotFound;
import br.com.jhonecmd.courses_api.modules.categories.courses.repositories.CourseRepository;

@Service
public class DeleteCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public void execute(String courseId) {

        var course = this.courseRepository.findById(UUID.fromString(courseId))
                .orElseThrow(() -> new CourseNotFound());

        this.courseRepository.delete(course);

        return;
    }
}
