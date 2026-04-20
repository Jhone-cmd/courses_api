package br.com.jhonecmd.courses_api.modules.users.controllers;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.jhonecmd.courses_api.modules.users.dto.CreateUserDTO;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;
import br.com.jhonecmd.courses_api.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

        @Autowired
        private MockMvc mvc;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RSAPrivateKey privateKey;

        @Autowired
        private RSAPublicKey publicKey;

        @BeforeEach
        void setup() {

                userRepository.deleteAll();
        }

        @Test
        @DisplayName("Should be able to create a new user.")
        public void should_be_able_to_create_a_new_user() throws Exception {

                var createdUserDTO = CreateUserDTO.builder().name("John doe").email("johndoe@email.com")
                                .password("0123456789")
                                .position("director")
                                .build();

                mvc.perform(MockMvcRequestBuilders.post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createdUserDTO)))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        @DisplayName("Should not be able to create a new user if him already exists.")
        public void should_not_be_able_to_create_a_new_user_if_him_already_exists() throws Exception {

                var user = UserEntity.builder().name("John doe").email("johndoe@email.com")
                                .password("encrypted_password")
                                .position(Position.director)
                                .build();

                this.userRepository.saveAndFlush(user);

                var createdUserDTO = CreateUserDTO.builder().name("John doe").email("johndoe@email.com")
                                .password("0123456789")
                                .position("director")
                                .build();

                mvc.perform(MockMvcRequestBuilders.post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createdUserDTO)))
                                .andExpect(MockMvcResultMatchers.status().isConflict());
        }

        @Test
        @DisplayName("Should not be able to create a new user if validations errors.")
        public void should_not_be_able_to_create_a_new_user_if_validations_errors() throws Exception {

                var createdUserDTO = CreateUserDTO.builder().name("John doe").email("").password("encrypted_password")
                                .position("director")
                                .build();

                mvc.perform(MockMvcRequestBuilders.post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createdUserDTO)))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("Should be able to fetch all users.")
        public void should_be_able_to_fetch_all_users() throws Exception {

                var user = UserEntity.builder().name("John doe").email("johndoe@email.com")
                                .password("encrypted_password")
                                .position(Position.director)
                                .build();

                this.userRepository.saveAndFlush(user);

                String token = TestUtils.generateToken(
                                user.getId(),
                                user.getPosition().toString(),
                                publicKey,
                                privateKey);

                mvc.perform(MockMvcRequestBuilders.get("/users")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }
}
