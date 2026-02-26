package br.com.jhonecmd.courses_api.modules.category.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    Optional<CategoryEntity> findByName(String name);
}
