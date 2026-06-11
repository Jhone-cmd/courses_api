package br.com.jhonecmd.courses_api.modules.users.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.modules.users.dto.UserResponseDTO;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@Service
public class FetchAllUserUseCase {

    private final UserRepository userRepository;

    FetchAllUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> execute() {
        var users = this.userRepository.findAll();

        return users.stream().map(user -> UserResponseDTO.builder().id(user.getId()).name(user.getName())
                .email(user.getEmail()).position(user.getPosition()).createAt(user.getCreatedAt()).build()).toList();
    }
}
