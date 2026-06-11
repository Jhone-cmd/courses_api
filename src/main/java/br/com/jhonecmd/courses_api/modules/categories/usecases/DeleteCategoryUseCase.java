package br.com.jhonecmd.courses_api.modules.categories.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CategoryNoFound;
import br.com.jhonecmd.courses_api.modules.categories.repositories.CategoryRepository;

@Service
public class DeleteCategoryUseCase {

    private final CategoryRepository categoryRepository;

    DeleteCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void execute(String categoryId) {
        var category = this.categoryRepository.findById(UUID.fromString(categoryId)).orElseThrow(() -> {
            throw new CategoryNoFound();
        });

        this.categoryRepository.delete(category);

        return;
    }
}
