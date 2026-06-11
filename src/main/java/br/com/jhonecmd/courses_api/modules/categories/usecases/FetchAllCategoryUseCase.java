package br.com.jhonecmd.courses_api.modules.categories.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.modules.categories.dto.CategoryResponseDTO;
import br.com.jhonecmd.courses_api.modules.categories.repositories.CategoryRepository;

@Service
public class FetchAllCategoryUseCase {

    private final CategoryRepository categoryRepository;

    FetchAllCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDTO> execute() {
        var categories = this.categoryRepository.findAll();

        return categories.stream()
                .map(category -> CategoryResponseDTO.builder().id(category.getId()).name(category.getName()).build())
                .toList();
    }
}
