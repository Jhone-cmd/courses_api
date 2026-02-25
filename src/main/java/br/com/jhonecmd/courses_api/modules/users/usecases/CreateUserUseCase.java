package br.com.jhonecmd.courses_api.modules.users.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.UserAlreadyExists;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public void execute(UserEntity userEntity) {
        this.userRepository.findByEmail(userEntity.getEmail()).ifPresent((user) -> {
            throw new UserAlreadyExists();
        });

        this.userRepository.save(userEntity);

        return;
    }
}
