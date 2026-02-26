package br.com.jhonecmd.courses_api.modules.category.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.modules.category.dto.CategoryResponseDTO;
import br.com.jhonecmd.courses_api.modules.category.repositories.CategoryRepository;

@Service
public class FetchAllCategoryUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponseDTO> execute() {
        var categories = this.categoryRepository.findAll();

        return categories.stream()
                .map(category -> CategoryResponseDTO.builder().id(category.getId()).name(category.getName()).build())
                .toList();
    }
}
