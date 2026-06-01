package br.com.jhonecmd.courses_api.modules.categories.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.exceptions.CategoryNoFound;
import br.com.jhonecmd.courses_api.modules.categories.dto.UpdateCategoryDTO;
import br.com.jhonecmd.courses_api.modules.categories.repositories.CategoryRepository;
import br.com.jhonecmd.courses_api.utils.CategoryMapper;

@Service
public class UpdateCategoryUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public void execute(String categoryId, UpdateCategoryDTO updateCategoryDTO) {
        var category = this.categoryRepository.findById(UUID.fromString(categoryId)).orElseThrow(() -> {
            throw new CategoryNoFound();
        });

        categoryMapper.updateEntityFromDto(updateCategoryDTO, category);

        this.categoryRepository.save(category);

        return;
    }
}
