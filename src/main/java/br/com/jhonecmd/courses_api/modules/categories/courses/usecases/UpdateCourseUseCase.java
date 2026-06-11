package br.com.jhonecmd.courses_api.modules.categories.courses.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CategoryNoFound;
import br.com.jhonecmd.courses_api.exceptions.CourseNotFound;
import br.com.jhonecmd.courses_api.exceptions.TeacherAlreadyAssignedException;
import br.com.jhonecmd.courses_api.exceptions.UserNotFound;
import br.com.jhonecmd.courses_api.modules.categories.courses.dto.CourseResponseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.dto.UpdateCourseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.categories.courses.repositories.CourseRepository;
import br.com.jhonecmd.courses_api.modules.categories.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.categories.repositories.CategoryRepository;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;
import br.com.jhonecmd.courses_api.utils.CourseMapper;

@Service
public class UpdateCourseUseCase {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final GetByCourseUseCase getByCourseUseCase;

    UpdateCourseUseCase(CourseRepository courseRepository, CourseMapper courseMapper, UserRepository userRepository,
            CategoryRepository categoryRepository, GetByCourseUseCase getByCourseUseCase) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.getByCourseUseCase = getByCourseUseCase;
    }

    public CourseResponseDTO execute(String id, UpdateCourseDTO updateCourseDTO) {

        CourseEntity course = this.courseRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CourseNotFound());

        if (updateCourseDTO.getCategoryName() != null) {
            CategoryEntity category = this.categoryRepository.findByName(updateCourseDTO.getCategoryName())
                    .orElseThrow(() -> new CategoryNoFound());

            course.setCategoryEntity(category);
        }

        if (updateCourseDTO.getTeacherName() != null) {
            UserEntity user = this.userRepository.findByName(updateCourseDTO.getTeacherName())
                    .orElseThrow(() -> new UserNotFound());

            boolean teacherIsBusy = this.courseRepository
                    .findByUserEntityIdAndIdNot(user.getId(), UUID.fromString(id))
                    .isPresent();

            if (teacherIsBusy) {
                throw new TeacherAlreadyAssignedException();
            }
        }

        courseMapper.updateEntityFromDto(updateCourseDTO, course);

        this.courseRepository.save(course);

        return this.getByCourseUseCase.execute(course.getId().toString());
    }

}
