package br.com.jhonecmd.courses_api.modules.users.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.UserNotFound;
import br.com.jhonecmd.courses_api.modules.users.dto.UpdateUserDTO;
import br.com.jhonecmd.courses_api.modules.users.dto.UserResponseDTO;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;
import br.com.jhonecmd.courses_api.utils.UserMapper;

@Service
public class UpdateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GetByUserUseCase getByUserUseCase;

    @Autowired
    private UserMapper userMapper;

    public UserResponseDTO execute(String userId, UpdateUserDTO updateUserDTO) {

        var user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> {
            throw new UserNotFound();
        });

        userMapper.updateEntityFromDto(updateUserDTO, user);

        this.userRepository.save(user);

        return this.getByUserUseCase.execute(user.getId().toString());

    }

}
