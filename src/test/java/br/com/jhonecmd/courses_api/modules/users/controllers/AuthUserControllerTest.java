package br.com.jhonecmd.courses_api.modules.users.controllers;

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

import br.com.jhonecmd.courses_api.modules.users.dto.AuthUserDTO;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;
import br.com.jhonecmd.courses_api.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {

        userRepository.deleteAll();

        var user = UserEntity.builder().name("John doe").email("user@email.com")
                .password(passwordEncoder.encode("0123456789"))
                .position(Position.director)
                .build();

        this.userRepository.saveAndFlush(user);
    }

    @Test
    @DisplayName("Should be able to authenticate a user.")
    public void should_be_able_to_authenticate_a_user() throws Exception {

        var authUserDTO = AuthUserDTO.builder().email("user@email.com").password("0123456789")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/users/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authUserDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should not be able to authenticate a user if incorrect email.")
    public void should_not_be_able_to_authenticate_a_user_if_incorrect_email() throws Exception {

        var authUserDTO = AuthUserDTO.builder().email("userjava@email.com")
                .password("0123456789")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/users/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authUserDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Should not be able to authenticate a user if wrong password.")
    public void should_not_be_able_to_authenticate_a_user_if_wrong_password() throws Exception {

        var authUserDTO = AuthUserDTO.builder().email("user@email.com").password("1234567891")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/users/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authUserDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
