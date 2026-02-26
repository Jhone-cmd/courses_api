package br.com.jhonecmd.courses_api.modules.category.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CategoryAlreadyExists;
import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.category.repositories.CategoryRepository;

@Service
public class CreateCategoryUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    public void execute(CategoryEntity categoryEntity) {
        this.categoryRepository.findByName(categoryEntity.getName()).ifPresent((category) -> {
            throw new CategoryAlreadyExists();
        });

        this.categoryRepository.save(categoryEntity);

        return;
    }
}
