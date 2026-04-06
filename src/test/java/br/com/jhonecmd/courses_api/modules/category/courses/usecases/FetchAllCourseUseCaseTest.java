package br.com.jhonecmd.courses_api.modules.category.courses.usecases;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;
import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;

@ExtendWith(MockitoExtension.class)
public class FetchAllCourseUseCaseTest {

        @InjectMocks
        private FetchAllCourseUseCase fetchAllCourseUseCase;

        @Mock
        private CourseRepository courseRepository;

        @Test
        @DisplayName("Should be able to list all courses.")
        public void should_be_able_to_list_all_courses() {

                var category = CategoryEntity.builder().name("Programming").build();

                var course1 = CourseEntity.builder()
                                .id(UUID.randomUUID())
                                .name("Java Advanced")
                                .categoryEntity(category)
                                .build();

                var course2 = CourseEntity.builder()
                                .id(UUID.randomUUID())
                                .name("Spring Boot Pro")
                                .categoryEntity(category)
                                .build();

                var expectedCourses = List.of(course1, course2);
                when(courseRepository.findAll()).thenReturn(expectedCourses);

                // Act
                var result = fetchAllCourseUseCase.execute(null);

                // Assert
                assertThat(result).hasSize(2);
                verify(courseRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should be able to list all courses with filter of status.")
        public void should_be_able_to_list_all_courses_with_filter_of_status() {

                var category = CategoryEntity.builder().name("Programming").build();
                var status = true;

                var course1 = CourseEntity.builder()
                                .id(UUID.randomUUID())
                                .name("Java Advanced")
                                .active(status)
                                .categoryEntity(category)
                                .build();

                var course2 = CourseEntity.builder()
                                .id(UUID.randomUUID())
                                .name("Spring Boot Pro")
                                .active(status)
                                .categoryEntity(category)
                                .build();

                var expectedCourses = List.of(course1, course2);
                when(courseRepository.findByActive(status)).thenReturn(expectedCourses);

                var result = fetchAllCourseUseCase.execute(status);
                assertThat(result).hasSize(2);
                verify(courseRepository, times(1)).findByActive(status);
        }
}
