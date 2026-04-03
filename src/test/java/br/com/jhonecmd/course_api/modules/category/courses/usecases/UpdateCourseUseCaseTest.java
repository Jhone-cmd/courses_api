package br.com.jhonecmd.course_api.modules.category.courses.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.jhonecmd.courses_api.exceptions.CategoryNoFound;
import br.com.jhonecmd.courses_api.exceptions.CourseNotFound;
import br.com.jhonecmd.courses_api.modules.category.courses.dto.CourseResponseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.dto.UpdateCourseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;
import br.com.jhonecmd.courses_api.modules.category.courses.usecases.GetByCourseUseCase;
import br.com.jhonecmd.courses_api.modules.category.courses.usecases.UpdateCourseUseCase;
import br.com.jhonecmd.courses_api.modules.category.courses.utils.CourseMapper;
import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.category.repositories.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class UpdateCourseUseCaseTest {

    @InjectMocks
    private UpdateCourseUseCase updateCourseUseCase;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private GetByCourseUseCase getByCourseUseCase;

    @Test
    @DisplayName("Should be able to update a course with success.")
    public void should_be_able_to_update_a_course() {

        var id = UUID.randomUUID();
        var updateDTO = UpdateCourseDTO.builder()
                .name("Gestão de TI")
                .categoryName("Tecnologia")
                .build();

        var existingCourse = CourseEntity.builder().id(id).name("Gestão de Tecnologia").build();
        var category = CategoryEntity.builder().id(UUID.randomUUID()).name("Tecnologia").build();
        var expectedResponse = CourseResponseDTO.builder().name("Gestão de TI").categoryName("Tecnologia").build();

        when(courseRepository.findById(id)).thenReturn(Optional.of(existingCourse));
        when(categoryRepository.findByName("Tecnologia")).thenReturn(Optional.of(category));
        when(getByCourseUseCase.execute(id.toString())).thenReturn(expectedResponse);

        var result = updateCourseUseCase.execute(id.toString(), updateDTO);

        assertThat(result.getName()).isEqualTo("Gestão de TI");
        verify(courseRepository, times(1)).save(any(CourseEntity.class));
        verify(courseMapper, times(1)).updateEntityFromDto(updateDTO, existingCourse);
    }

    @Test
    @DisplayName("Should throw an exception if course is not found.")
    public void should_not_be_able_to_update_a_non_existing_course() {

        var id = UUID.randomUUID();
        var updateDTO = UpdateCourseDTO.builder().name("Gestão de TI").build();

        when(courseRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> updateCourseUseCase.execute(id.toString(), updateDTO))
                .isInstanceOf(CourseNotFound.class);

        verify(courseRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw an exception if category is provided but not found.")
    public void should_throw_exception_if_category_not_found() {

        var id = UUID.randomUUID();
        var updateDTO = UpdateCourseDTO.builder()
                .name("Gestão de TI")
                .categoryName("Tecnologia")
                .build();

        var existingCourse = CourseEntity.builder().id(id).build();

        when(courseRepository.findById(id)).thenReturn(Optional.of(existingCourse));
        when(categoryRepository.findByName("Tecnologia")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateCourseUseCase.execute(id.toString(), updateDTO))
                .isInstanceOf(CategoryNoFound.class);

        verify(courseRepository, never()).save(any());
    }
}