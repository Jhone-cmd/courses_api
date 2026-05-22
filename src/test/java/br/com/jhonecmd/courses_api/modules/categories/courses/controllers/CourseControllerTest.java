package br.com.jhonecmd.courses_api.modules.categories.courses.controllers;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import br.com.jhonecmd.courses_api.modules.categories.courses.dto.ChangeStatusCourseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.dto.UpdateCourseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.categories.courses.repositories.CourseRepository;
import br.com.jhonecmd.courses_api.modules.categories.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.categories.repositories.CategoryRepository;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;
import br.com.jhonecmd.courses_api.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CourseControllerTest {

        @Autowired
        private MockMvc mvc;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private CourseRepository courseRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        private UserEntity user;

        @Autowired
        private RSAPrivateKey privateKey;

        @Autowired
        private RSAPublicKey publicKey;

        @BeforeEach
        void setup() {

                courseRepository.deleteAll();
                userRepository.deleteAll();
                categoryRepository.deleteAll();

                user = UserEntity.builder()
                                .name("admin")
                                .email("admin@email.com")
                                .password(passwordEncoder.encode("0123456789"))
                                .position(Position.rector)
                                .build();

                this.userRepository.saveAndFlush(user);
        }

        @Test
        @DisplayName("Should be able to fetch all courses.")
        public void should_be_able_to_fetch_all_courses() throws Exception {

                var category = CategoryEntity.builder().name("Tecnologia").build();
                this.categoryRepository.saveAndFlush(category);

                var course = CourseEntity.builder().name("Tecnologia da Informação").categoryEntity(category)
                                .active(true)
                                .build();

                var course2 = CourseEntity.builder().name("Engenharia de Software").categoryEntity(category)
                                .active(true)
                                .build();

                this.courseRepository.saveAllAndFlush(List.of(course, course2));

                String token = TestUtils.generateToken(
                                user.getId(),
                                user.getPosition().toString(),
                                publicKey,
                                privateKey);

                mvc.perform(MockMvcRequestBuilders.get("/courses")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("Should be able to get specific course.")
        public void should_be_able_to_get_specific_course() throws Exception {

                var category = CategoryEntity.builder().name("Tecnologia").build();
                this.categoryRepository.saveAndFlush(category);

                var course = CourseEntity.builder().name("Tecnologia da Informação").categoryEntity(category)
                                .active(true)
                                .build();

                var course2 = CourseEntity.builder().name("Engenharia de Software").categoryEntity(category)
                                .active(true)
                                .build();

                this.courseRepository.saveAllAndFlush(List.of(course, course2));

                String token = TestUtils.generateToken(
                                user.getId(),
                                user.getPosition().toString(),
                                publicKey,
                                privateKey);

                mvc.perform(MockMvcRequestBuilders.get("/courses/{id}", course.getId().toString())
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(course.getId().toString()));
        }

        @Test
        @DisplayName("Should be able to delete a course.")
        public void should_be_able_to_delete_a_course() throws Exception {

                var category = CategoryEntity.builder().name("Tecnologia").build();
                this.categoryRepository.saveAndFlush(category);

                var course = CourseEntity.builder().name("Tecnologia da Informação").categoryEntity(category)
                                .active(true)
                                .build();

                var course2 = CourseEntity.builder().name("Engenharia de Software").categoryEntity(category)
                                .active(true)
                                .build();

                this.courseRepository.saveAllAndFlush(List.of(course, course2));

                String token = TestUtils.generateToken(
                                user.getId(),
                                user.getPosition().toString(),
                                publicKey,
                                privateKey);

                mvc.perform(MockMvcRequestBuilders.delete("/courses/{id}", course.getId().toString())
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isNoContent());
        }

        @Test
        @DisplayName("Should be able to change the course status.")
        public void should_be_able_to_change_the_course_status() throws Exception {

                var category = CategoryEntity.builder().name("Tecnologia").build();
                this.categoryRepository.saveAndFlush(category);

                var course = CourseEntity.builder().name("Tecnologia da Informação").categoryEntity(category)
                                .active(true)
                                .build();

                var course2 = CourseEntity.builder().name("Engenharia de Software").categoryEntity(category)
                                .active(false)
                                .build();

                this.courseRepository.saveAllAndFlush(List.of(course, course2));

                String token = TestUtils.generateToken(
                                user.getId(),
                                user.getPosition().toString(),
                                publicKey,
                                privateKey);

                var changeStatusCourseDTO = ChangeStatusCourseDTO.builder().active(true).build();

                mvc.perform(MockMvcRequestBuilders.patch("/courses/{id}/active", course2.getId().toString())
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(changeStatusCourseDTO)))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("Should be able to update a course.")
        public void should_be_able_to_update_a_course() throws Exception {

                var category = CategoryEntity.builder().name("Tecnologia").build();
                this.categoryRepository.saveAndFlush(category);

                var course = CourseEntity.builder().name("Tecnologia da Informação").categoryEntity(category)
                                .active(true)
                                .build();

                var course2 = CourseEntity.builder().name("Engenharia de Software").categoryEntity(category)
                                .active(false)
                                .build();

                this.courseRepository.saveAllAndFlush(List.of(course, course2));

                String token = TestUtils.generateToken(
                                user.getId(),
                                user.getPosition().toString(),
                                publicKey,
                                privateKey);

                var updateCourseDTO = UpdateCourseDTO.builder().description(
                                "A Engenharia de Software é a aplicação de métodos científicos e princípios de engenharia para o desenvolvimento, operação e manutenção de software de alta qualidade, confiável e escalável. Envolve gerenciar todo o ciclo de vida do sistema, desde requisitos até testes e manutenção, frequentemente utilizando metodologias ágeis como Scrum e Kanban.")
                                .build();

                mvc.perform(MockMvcRequestBuilders.put("/courses/{id}", course2.getId().toString())
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(updateCourseDTO)))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }
}
