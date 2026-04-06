package br.com.jhonecmd.courses_api.modules.users.usecases;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.jhonecmd.courses_api.exceptions.InvalidCredentials;
import br.com.jhonecmd.courses_api.modules.users.dto.AuthUserDTO;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthenticateUserUseCaseTest {

        @InjectMocks
        private AuthenticateUserUseCase authenticateUserUseCase;

        @Mock
        private UserRepository userRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @BeforeEach
        void setup() {

                try {
                        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                        kpg.initialize(2048);
                        KeyPair kp = kpg.generateKeyPair();
                        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
                        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

                        ReflectionTestUtils.setField(authenticateUserUseCase, "publicKey", publicKey);
                        ReflectionTestUtils.setField(authenticateUserUseCase, "privateKey", privateKey);

                } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException("Erro: Algoritmo RSA não encontrado no ambiente", e);
                }
        }

        @Test
        @DisplayName("Should not be able to authenticate with incorrect email.")
        public void should_not_be_able_to_authenticate_with_incorrect_email() {

                var authDTO = new AuthUserDTO("user@email.com", "encoded_password");

                when(userRepository.findByEmail(authDTO.getEmail()))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> authenticateUserUseCase.execute(authDTO))
                                .isInstanceOf(InvalidCredentials.class);
                verify(passwordEncoder, never()).matches(anyString(), anyString());
        }

        @Test
        @DisplayName("Should not be able to authenticate with wrong password.")
        public void should_not_be_able_to_authenticate_with_wrong_password() {

                var authDTO = new AuthUserDTO("user@email.com", "wrong_password");
                var candidate = UserEntity.builder()
                                .email("user@email.com")
                                .password("encoded_password")
                                .build();

                when(userRepository.findByEmail(authDTO.getEmail()))
                                .thenReturn(Optional.of(candidate));

                when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

                assertThatThrownBy(() -> authenticateUserUseCase.execute(authDTO))
                                .isInstanceOf(InvalidCredentials.class);
        }

        @Test
        @DisplayName("Should be able possible to authenticate an user and return the token.")
        public void should_be_able_to_authenticate_an_user() {

                var password = "password123";
                var user = UserEntity.builder()
                                .id(UUID.randomUUID())
                                .email("user@email.com")
                                .password("encoded_password")
                                .position(Position.director)
                                .build();

                var authDTO = new AuthUserDTO("user@email.com", password);

                when(userRepository.findByEmail(authDTO.getEmail()))
                                .thenReturn(Optional.of(user));

                when(passwordEncoder.matches(password, user.getPassword()))
                                .thenReturn(true);

                var result = authenticateUserUseCase.execute(authDTO);

                assertThat(result.getAccess_token()).isNotNull();
                assertThat(result.getExpiresAt()).isGreaterThan(0);
                verify(userRepository, times(1)).findByEmail(authDTO.getEmail());
        }
}
