package br.com.jhonecmd.courses_api.modules.categories.usecases;

import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CategoryAlreadyExists;
import br.com.jhonecmd.courses_api.modules.categories.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.categories.repositories.CategoryRepository;

@Service
public class CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    CreateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void execute(CategoryEntity categoryEntity) {
        this.categoryRepository.findByName(categoryEntity.getName()).ifPresent((category) -> {
            throw new CategoryAlreadyExists();
        });

        this.categoryRepository.save(categoryEntity);

        return;
    }
}
