package br.com.jhonecmd.courses_api.modules.users.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.UserNotFound;
import br.com.jhonecmd.courses_api.modules.users.dto.UserResponseDTO;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@Service
public class GetByUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO execute(String userId) {

        var user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> {
            throw new UserNotFound();
        });

        var userResponseDTO = UserResponseDTO.builder().id(user.getId()).name(user.getName()).email(user.getEmail())
                .position(user.getPosition()).createAt(user.getCreatedAt()).build();

        return userResponseDTO;
    }
}
