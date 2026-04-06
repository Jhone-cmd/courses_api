package br.com.jhonecmd.courses_api.modules.users.usecases;

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

import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity.Position;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class FetchAllUserUseCaseTest {

    @InjectMocks
    private FetchAllUserUseCase fetchAllUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should be able to list all user.")
    public void should_be_able_to_list_all_user() {

        var user1 = UserEntity.builder()
                .id(UUID.randomUUID())
                .email("user@email.com")
                .password("encoded_password")
                .position(Position.director)
                .build();

        var user2 = UserEntity.builder()
                .id(UUID.randomUUID())
                .email("user@email.com")
                .password("encoded_password")
                .position(Position.coordinator)
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        var result = fetchAllUserUseCase.execute();

        assertThat(result).hasSize(2);
        verify(userRepository, times(1)).findAll();
    }
}
