package br.com.jhonecmd.course_api.modules.users.usecases;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.jhonecmd.courses_api.exceptions.UserAlreadyExists;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;
import br.com.jhonecmd.courses_api.modules.users.usecases.CreateUserUseCase;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should not be able to create an user if email already exists.")
    public void should_not_be_able_to_create_an_user_if_email_already_exists() {

        var user = new UserEntity();
        user.setEmail("candidate@email.com");

        when(this.userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(new UserEntity()));

        assertThatThrownBy(() -> this.createUserUseCase.execute(user))
                .isInstanceOf(UserAlreadyExists.class);
    }

    @Test
    @DisplayName("Should be able to create an user.")
    public void should_be_able_to_create_an_user() {

        var user = new UserEntity();
        user.setEmail("candidate@email.com");
        user.setPassword("password123");

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(user.getPassword()))
                .thenReturn("password_encrypted");

        this.createUserUseCase.execute(user);

        assertThat(user.getPassword()).isEqualTo("password_encrypted");

        verify(userRepository, times(1)).save(user);
    }
}
