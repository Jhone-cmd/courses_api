package br.com.jhonecmd.courses_api.modules.users.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.UserNotFound;
import br.com.jhonecmd.courses_api.modules.users.dto.ChangePasswordUserDTO;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@Service
public class ChangePasswordUseCase {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    ChangePasswordUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
