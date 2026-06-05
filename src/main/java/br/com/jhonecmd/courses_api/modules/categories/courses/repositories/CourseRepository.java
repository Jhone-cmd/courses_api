package br.com.jhonecmd.courses_api.modules.categories.courses.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonecmd.courses_api.modules.categories.courses.entities.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findByName(String name);

    List<CourseEntity> findByActive(Boolean active);

    Optional<CourseEntity> findByUserEntityIdAndIdNot(UUID userEntityId, UUID courseId);
}
