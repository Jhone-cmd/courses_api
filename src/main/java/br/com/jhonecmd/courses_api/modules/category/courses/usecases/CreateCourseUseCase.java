package br.com.jhonecmd.courses_api.modules.category.courses.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CourseAlreadyExists;
import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;

@Service
public class CreateCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public void execute(CourseEntity courseEntity) {
        this.courseRepository.findByName(courseEntity.getName()).ifPresent((course) -> {
            throw new CourseAlreadyExists();
        });

        this.courseRepository.save(courseEntity);

        return;
    }
}
