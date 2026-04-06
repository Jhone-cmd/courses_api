package br.com.jhonecmd.courses_api.modules.category.controllers;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.jhonecmd.courses_api.modules.category.courses.dto.CreateCourseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;
import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.category.repositories.CategoryRepository;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;
import br.com.jhonecmd.courses_api.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CreateCourseControllerTest {

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
    @DisplayName("Should be able to create a new course.")
    public void should_be_able_to_create_a_new_course() throws Exception {

        var teacher = UserEntity.builder()
                .name("john doe")
                .email("johndoe@email.com")
                .password(passwordEncoder.encode("0123456789"))
                .position(Position.teacher)
                .build();

        this.userRepository.saveAndFlush(teacher);

        var category = CategoryEntity.builder().name("Tecnologia").build();
        this.categoryRepository.saveAndFlush(category);

        var createCourseDTO = CreateCourseDTO.builder()
                .name("Tecnologia da Informação").active(true).teacherName(teacher.getName())
                .categoryId(category.getId())
                .build();

        String token = TestUtils.generateToken(
                user.getId(),
                user.getPosition().toString(),
                publicKey,
                privateKey);

        mvc.perform(MockMvcRequestBuilders.post("/categories/{id}/courses", category.getId().toString())
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createCourseDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Should not be able to create a new course if him already exists.")
    public void should_not_be_able_to_create_a_new_course_if_him_already_exists() throws Exception {

        var teacher = UserEntity.builder()
                .name("john doe")
                .email("johndoe@email.com")
                .password(passwordEncoder.encode("0123456789"))
                .position(Position.teacher)
                .build();

        this.userRepository.saveAndFlush(teacher);

        var category = CategoryEntity.builder().name("Tecnologia").build();
        this.categoryRepository.saveAndFlush(category);

        var course = CourseEntity.builder().name("Tecnologia da Informação").categoryEntity(category).active(true)
                .build();

        this.courseRepository.saveAndFlush(course);

        var createCourseDTO = CreateCourseDTO.builder()
                .name("Tecnologia da Informação").active(true).teacherName(teacher.getName())
                .categoryId(category.getId())
                .build();

        String token = TestUtils.generateToken(
                user.getId(),
                user.getPosition().toString(),
                publicKey,
                privateKey);

        mvc.perform(MockMvcRequestBuilders.post("/categories/{id}/courses", category.getId().toString())
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createCourseDTO)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Should not be able to create a new course if validations errors.")
    public void should_not_be_able_to_create_a_new_course_if_validations_errors() throws Exception {

        var teacher = UserEntity.builder()
                .name("john doe")
                .email("johndoe@email.com")
                .password(passwordEncoder.encode("0123456789"))
                .position(Position.teacher)
                .build();

        this.userRepository.saveAndFlush(teacher);

        var category = CategoryEntity.builder().name("Tecnologia").build();
        this.categoryRepository.saveAndFlush(category);

        var createCourseDTO = CreateCourseDTO.builder()
                .name("").active(true).teacherName("")
                .categoryId(category.getId())
                .build();

        String token = TestUtils.generateToken(
                user.getId(),
                user.getPosition().toString(),
                publicKey,
                privateKey);

        mvc.perform(MockMvcRequestBuilders.post("/categories/{id}/courses", category.getId().toString())
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createCourseDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
