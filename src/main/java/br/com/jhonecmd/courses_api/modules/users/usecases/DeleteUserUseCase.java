package br.com.jhonecmd.courses_api.modules.users.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.UserNotFound;
import br.com.jhonecmd.courses_api.modules.users.repositories.UserRepository;

@Service
public class DeleteUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public void execute(String userId) {

        var user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> {
            throw new UserNotFound();
        });

        this.userRepository.delete(user);

        return;

    }
}
