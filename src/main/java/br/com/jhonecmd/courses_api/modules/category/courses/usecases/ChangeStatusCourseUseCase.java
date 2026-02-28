package br.com.jhonecmd.courses_api.modules.category.courses.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CourseNotFound;
import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;

@Service
public class ChangeStatusCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public void execute(String id, Boolean active) {

        CourseEntity course = this.courseRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CourseNotFound());

        course.setActive(active);

        this.courseRepository.save(course);

        return;
    }
}
