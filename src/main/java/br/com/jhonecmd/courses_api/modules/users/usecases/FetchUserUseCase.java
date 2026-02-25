package br.com.jhonecmd.courses_api.modules.users.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.modules.users.dto.UserResponseDTO;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@Service
public class FetchUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO> execute() {
        var users = this.userRepository.findAll();

        return users.stream().map(user -> UserResponseDTO.builder().id(user.getId()).name(user.getName())
                .email(user.getEmail()).position(user.getPosition()).createAt(user.getCreateAt()).build()).toList();
    }
}
