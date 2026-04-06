package br.com.jhonecmd.courses_api.modules.category.usecases;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.jhonecmd.courses_api.exceptions.CategoryAlreadyExists;
import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.category.repositories.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private CreateCategoryUseCase createCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should not be able to create a category if name already exists.")
    public void should_not_be_able_to_create_a_category_if_name_already_exists() {

        var category = new CategoryEntity();
        category.setName("Tecnologia");

        when(this.categoryRepository.findByName(category.getName()))
                .thenReturn(Optional.of(new CategoryEntity()));

        assertThatThrownBy(() -> this.createCategoryUseCase.execute(category))
                .isInstanceOf(CategoryAlreadyExists.class);
    }

    @Test
    @DisplayName("Should be able to create a category.")
    public void should_be_able_to_create_a_category() {

        var category = new CategoryEntity();
        category.setName("Tecnologia");

        when(categoryRepository.findByName(category.getName()))
                .thenReturn(Optional.empty());

        this.createCategoryUseCase.execute(category);

        assertThat(category.getName()).isEqualTo("Tecnologia");
        verify(categoryRepository, times(1)).save(category);
    }
}
