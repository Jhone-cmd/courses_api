package br.com.jhonecmd.courses_api.modules.users.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.UserAlreadyExists;
import br.com.jhonecmd.courses_api.exceptions.UserNotFound;
import br.com.jhonecmd.courses_api.modules.users.dto.ChangePasswordUserDTO;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;
import br.com.jhonecmd.courses_api.utils.UserMapper;

@Service
public class ChangePasswordUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void execute(ChangePasswordUserDTO changePasswordUserDTO) {

        UserEntity user = this.userRepository.findByEmail(changePasswordUserDTO.getEmail()).orElseThrow(() -> {
            throw new UserNotFound();
        });

        var password = this.passwordEncoder.encode(changePasswordUserDTO.getPassword());

        user.setPassword(password);

        this.userRepository.save(user);

        return;
    }
}
