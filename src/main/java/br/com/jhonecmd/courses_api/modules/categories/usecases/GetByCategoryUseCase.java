package br.com.jhonecmd.courses_api.modules.categories.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CategoryNoFound;
import br.com.jhonecmd.courses_api.modules.categories.dto.CategoryResponseDTO;
import br.com.jhonecmd.courses_api.modules.categories.repositories.CategoryRepository;

@Service
public class GetByCategoryUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponseDTO execute(String categoryId) {
        var category = this.categoryRepository.findById(UUID.fromString(categoryId)).orElseThrow(() -> {
            throw new CategoryNoFound();
        });

        var categoryResponseDTO = CategoryResponseDTO.builder().id(category.getId()).name(category.getName()).build();

        return categoryResponseDTO;
    }
}
