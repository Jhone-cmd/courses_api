package br.com.jhonecmd.courses_api.modules.categories.controllers;

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

import br.com.jhonecmd.courses_api.modules.categories.courses.repositories.CourseRepository;
import br.com.jhonecmd.courses_api.modules.categories.dto.CreateCategoryDTO;
import br.com.jhonecmd.courses_api.modules.categories.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.categories.repositories.CategoryRepository;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;
import br.com.jhonecmd.courses_api.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CategoryControllerTest {

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
        @DisplayName("Should be able to create a new category.")
        public void should_be_able_to_create_a_new_category() throws Exception {

                var createCategoryDTO = CreateCategoryDTO.builder()
                                .name("Tecnologia")
                                .build();

                String token = TestUtils.generateToken(
                                user.getId(),
                                user.getPosition().toString(),
                                publicKey,
                                privateKey);

                mvc.perform(MockMvcRequestBuilders.post("/categories")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createCategoryDTO)))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        @DisplayName("Should not be able to create a new category if him already exists.")
        public void should_not_be_able_to_create_a_new_category_if_him_already_exists() throws Exception {

                var category = CategoryEntity.builder().name("Tecnologia").build();
                this.categoryRepository.saveAndFlush(category);

                var createCategoryDTO = CreateCategoryDTO.builder()
                                .name("Tecnologia")
                                .build();

                String token = TestUtils.generateToken(
                                user.getId(),
                                user.getPosition().toString(),
                                publicKey,
                                privateKey);

                mvc.perform(MockMvcRequestBuilders.post("/categories")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createCategoryDTO)))
                                .andExpect(MockMvcResultMatchers.status().isConflict());
        }

        @Test
        @DisplayName("Should not be able to create a new category if validations errors.")
        public void should_not_be_able_to_create_a_new_category_if_validations_errors() throws Exception {

                var createCategoryDTO = CreateCategoryDTO.builder()
                                .name("")
                                .build();

                String token = TestUtils.generateToken(
                                user.getId(),
                                user.getPosition().toString(),
                                publicKey,
                                privateKey);

                mvc.perform(MockMvcRequestBuilders.post("/categories")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createCategoryDTO)))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("Should be able to fetch all categories.")
        public void should_be_able_to_fetch_all_categories() throws Exception {

                var category = CategoryEntity.builder().name("Tecnologia").build();
                this.categoryRepository.saveAndFlush(category);

                String token = TestUtils.generateToken(
                                user.getId(),
                                user.getPosition().toString(),
                                publicKey,
                                privateKey);

                mvc.perform(MockMvcRequestBuilders.get("/categories")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }
}
