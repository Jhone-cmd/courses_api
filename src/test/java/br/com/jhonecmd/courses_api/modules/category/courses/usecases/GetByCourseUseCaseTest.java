package br.com.jhonecmd.courses_api.modules.category.courses.usecases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.jhonecmd.courses_api.exceptions.CourseNotFound;
import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;
import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;

@ExtendWith(MockitoExtension.class)
public class GetByCourseUseCaseTest {

    @InjectMocks
    private GetByCourseUseCase getByCourseUseCase;

    @Mock
    private CourseRepository courseRepository;

    @Test
    @DisplayName("Should be able to search for a specific course..")
    public void should_be_able_to_search_for_a_specific_course() {

        var category = CategoryEntity.builder().name("Programming").build();

        var courseId = UUID.randomUUID();
        var course = CourseEntity.builder()
                .id(courseId)
                .name("Tecnologia")
                .active(true)
                .categoryEntity(category)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        var result = this.getByCourseUseCase.execute(courseId.toString());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(courseId);
        assertThat(result.getName()).isEqualTo("Tecnologia");
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    @DisplayName("Should not be able to search for a specific course if him not found.")
    public void should_not_be_able_to_search_for_a_specific_course_if_him_not_found() {

        var courseId = UUID.randomUUID();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getByCourseUseCase.execute(courseId.toString()))
                .isInstanceOf(CourseNotFound.class);
    }

}
