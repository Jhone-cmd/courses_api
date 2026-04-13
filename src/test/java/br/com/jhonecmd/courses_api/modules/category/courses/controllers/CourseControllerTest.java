package br.com.jhonecmd.courses_api.modules.category.courses.controllers;

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

        var course = CourseEntity.builder().name("Tecnologia da Informação").categoryEntity(category).active(true)
                .build();

        var course2 = CourseEntity.builder().name("Engenharia de Software").categoryEntity(category).active(true)
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

        var course = CourseEntity.builder().name("Tecnologia da Informação").categoryEntity(category).active(true)
                .build();

        var course2 = CourseEntity.builder().name("Engenharia de Software").categoryEntity(category).active(true)
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
}
