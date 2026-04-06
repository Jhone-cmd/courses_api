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

@ExtendWith(MockitoExtension.class)
public class ChangeStatusCourseUseCaseTest {

    @InjectMocks
    private ChangeStatusCourseUseCase changeStatusCourseUseCase;

    @Mock
    private CourseRepository courseRepository;

    @Test
    @DisplayName("Should be able to change status a course.")
    public void should_be_able_to_change_status_a_course() {

        var status = true;
        var courseId = UUID.randomUUID();
        var course = CourseEntity.builder()
                .id(courseId)
                .name("Tecnologia")
                .active(false)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        this.changeStatusCourseUseCase.execute(courseId.toString(), status);

        assertThat(course.getActive()).isEqualTo(status);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("Should not be able to change status a course if him not found.")
    public void should_not_be_able_to_change_status_a_course_if_him_not_found() {

        var status = true;

        var courseId = UUID.randomUUID();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> changeStatusCourseUseCase.execute(courseId.toString(), status))
                .isInstanceOf(CourseNotFound.class);
    }
}
