package br.com.jhonecmd.courses_api.modules.users.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.UserAlreadyExists;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(UserEntity userEntity) {
        this.userRepository.findByEmail(userEntity.getEmail()).ifPresent((user) -> {
            throw new UserAlreadyExists();
        });

        var password = this.passwordEncoder.encode(userEntity.getPassword());

        userEntity.setPassword(password);

        this.userRepository.save(userEntity);

        return;
    }
}
