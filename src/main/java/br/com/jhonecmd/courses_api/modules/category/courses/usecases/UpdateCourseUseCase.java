package br.com.jhonecmd.courses_api.modules.category.courses.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CategoryNoFound;
import br.com.jhonecmd.courses_api.exceptions.CourseNotFound;
import br.com.jhonecmd.courses_api.modules.category.courses.dto.CourseResponseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.dto.UpdateCourseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;
import br.com.jhonecmd.courses_api.modules.category.courses.utils.CourseMapper;
import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.category.repositories.CategoryRepository;

@Service
public class UpdateCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GetByCourseUseCase getByCourseUseCase;

    public CourseResponseDTO execute(String id, UpdateCourseDTO updateCourseDTO) {

        CourseEntity course = this.courseRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CourseNotFound());

        if (updateCourseDTO.getCategoryName() != null) {
            CategoryEntity category = this.categoryRepository.findByName(updateCourseDTO.getCategoryName())
                    .orElseThrow(() -> new CategoryNoFound());

            course.setCategoryId(category.getId());
            course.setCategoryEntity(category);
        }

        courseMapper.updateEntityFromDto(updateCourseDTO, course);

        this.courseRepository.save(course);

        return this.getByCourseUseCase.execute(course.getId().toString());
    }

}
