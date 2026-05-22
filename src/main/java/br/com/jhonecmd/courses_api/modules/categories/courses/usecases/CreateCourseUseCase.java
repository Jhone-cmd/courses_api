package br.com.jhonecmd.courses_api.modules.categories.courses.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CategoryNoFound;
import br.com.jhonecmd.courses_api.exceptions.CourseAlreadyExists;
import br.com.jhonecmd.courses_api.exceptions.UserIsNotTeacher;
import br.com.jhonecmd.courses_api.exceptions.UserNotFound;
import br.com.jhonecmd.courses_api.modules.categories.courses.dto.CreateCourseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.categories.courses.repositories.CourseRepository;
import br.com.jhonecmd.courses_api.modules.categories.repositories.CategoryRepository;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@Service
public class CreateCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void execute(String categoryId, CreateCourseDTO createCourseDTO) {
        this.courseRepository.findByName(createCourseDTO.getName()).ifPresent((course) -> {
            throw new CourseAlreadyExists();
        });

        var user = this.userRepository.findByName(createCourseDTO.getTeacherName())
                .orElseThrow(() -> new UserNotFound());

        if (user.getPosition() != Position.teacher) {
            throw new UserIsNotTeacher();
        }

        var category = this.categoryRepository.findById(UUID.fromString(categoryId))
                .orElseThrow(() -> new CategoryNoFound());

        var courseEntity = CourseEntity.builder().name(createCourseDTO.getName()).userEntity(user)
                .description(createCourseDTO.getDescription()).active(false).categoryEntity(category)
                .build();

        this.courseRepository.save(courseEntity);
        return;
    }
}
