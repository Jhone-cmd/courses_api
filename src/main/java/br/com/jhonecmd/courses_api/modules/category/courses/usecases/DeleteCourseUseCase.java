package br.com.jhonecmd.courses_api.modules.category.courses.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public void execute(String id) {

        var course = this.courseRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + id));

        this.courseRepository.delete(course);

        return;
    }
}
