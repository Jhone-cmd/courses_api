package br.com.jhonecmd.courses_api.modules.category.courses.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.jhonecmd.courses_api.exceptions.CategoryNoFound;
import br.com.jhonecmd.courses_api.exceptions.CourseAlreadyExists;
import br.com.jhonecmd.courses_api.exceptions.UserIsNotTeacher;
import br.com.jhonecmd.courses_api.exceptions.UserNotFound;
import br.com.jhonecmd.courses_api.modules.category.courses.dto.CreateCourseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;

import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.category.repositories.CategoryRepository;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CreateCourseUseCaseTest {

        @InjectMocks
        private CreateCourseUseCase createCourseUseCase;

        @Mock
        private CourseRepository courseRepository;

        @Mock
        private UserRepository userRepository;

        @Mock
        private CategoryRepository categoryRepository;

        @Test
        @DisplayName("Should not be able to create course if name exists.")
        public void should_not_be_able_to_create_course_if_name_exists() {

                var categoryId = UUID.randomUUID();
                var course = CourseEntity.builder().name("Tecnologia da Informação").build();

                var courseDTO = CreateCourseDTO.builder().name("Tecnologia da Informação").categoryId(categoryId)
                                .build();

                when(courseRepository.findByName(course.getName()))
                                .thenReturn(Optional.of(new CourseEntity()));

                assertThatThrownBy(() -> createCourseUseCase.execute(categoryId.toString(), courseDTO))
                                .isInstanceOf(CourseAlreadyExists.class);

                verify(courseRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should not be able to create course if user not found.")
        public void should_not_be_able_to_create_course_if_user_not_found() {

                var categoryId = UUID.randomUUID();
                var user = UserEntity.builder()
                                .name("john doe")
                                .position(Position.teacher)
                                .build();

                var courseDTO = CreateCourseDTO.builder()
                                .name("Tecnologia da Informação")
                                .categoryId(categoryId)
                                .teacherName(user.getName())
                                .build();

                when(courseRepository.findByName(courseDTO.getName())).thenReturn(Optional.empty());
                when(userRepository.findByName(courseDTO.getTeacherName())).thenReturn(Optional.empty());

                assertThatThrownBy(() -> createCourseUseCase.execute(categoryId.toString(), courseDTO))
                                .isInstanceOf(UserNotFound.class);

                verify(courseRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should not be able to create course if user is not teacher.")
        public void should_not_be_able_to_create_course_if_user_is_not_teacher() {

                var categoryId = UUID.randomUUID();
                var user = UserEntity.builder()
                                .name("john doe")
                                .position(Position.coordinator)
                                .build();

                var courseDTO = CreateCourseDTO.builder()
                                .name("Tecnologia da Informação")
                                .categoryId(categoryId)
                                .teacherName(user.getName())
                                .build();

                when(courseRepository.findByName(courseDTO.getName())).thenReturn(Optional.empty());
                when(userRepository.findByName(courseDTO.getTeacherName())).thenReturn(Optional.of(user));

                assertThatThrownBy(() -> createCourseUseCase.execute(categoryId.toString(), courseDTO))
                                .isInstanceOf(UserIsNotTeacher.class);

                verify(courseRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should not be able to create course if category not found.")
        public void should_not_be_able_to_create_course_if_category_not_found() {

                var categoryId = UUID.randomUUID();
                var user = UserEntity.builder()
                                .name("john doe")
                                .position(Position.teacher)
                                .build();

                var courseDTO = CreateCourseDTO.builder()
                                .name("Tecnologia da Informação")
                                .categoryId(categoryId)
                                .teacherName(user.getName())
                                .build();

                when(courseRepository.findByName(courseDTO.getName())).thenReturn(Optional.empty());
                when(userRepository.findByName(courseDTO.getTeacherName())).thenReturn(Optional.of(user));
                when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

                assertThatThrownBy(() -> createCourseUseCase.execute(categoryId.toString(), courseDTO))
                                .isInstanceOf(CategoryNoFound.class);

                verify(courseRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should be able to create a new course.")
        public void should_be_able_to_create_a_new_course() {

                var categoryId = UUID.randomUUID();
                var category = CategoryEntity.builder().id(categoryId).build();
                var user = UserEntity.builder()
                                .name("john doe")
                                .position(Position.teacher)
                                .build();

                var courseDTO = CreateCourseDTO.builder()
                                .name("Tecnologia da Informação")
                                .categoryId(categoryId)
                                .teacherName(user.getName())
                                .build();

                when(courseRepository.findByName(courseDTO.getName())).thenReturn(Optional.empty());
                when(userRepository.findByName(courseDTO.getTeacherName())).thenReturn(Optional.of(user));
                when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

                this.createCourseUseCase.execute(categoryId.toString(), courseDTO);

                ArgumentCaptor<CourseEntity> courseCaptor = ArgumentCaptor.forClass(CourseEntity.class);
                verify(courseRepository).save(courseCaptor.capture());
                verify(courseRepository, times(1)).save(any(CourseEntity.class));

                var savedCourse = courseCaptor.getValue();
                assertThat(savedCourse.getName()).isEqualTo(courseDTO.getName());
                assertThat(savedCourse.getUserEntity().getName()).isEqualTo(user.getName());
        }
}
