package br.com.jhonecmd.courses_api.modules.users.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByEmail(String email);
}
