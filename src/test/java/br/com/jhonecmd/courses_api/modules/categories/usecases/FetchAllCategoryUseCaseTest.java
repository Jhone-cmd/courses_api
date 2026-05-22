package br.com.jhonecmd.courses_api.modules.categories.usecases;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.jhonecmd.courses_api.modules.categories.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.categories.repositories.CategoryRepository;
import br.com.jhonecmd.courses_api.modules.categories.usecases.FetchAllCategoryUseCase;

@ExtendWith(MockitoExtension.class)
public class FetchAllCategoryUseCaseTest {

    @InjectMocks
    private FetchAllCategoryUseCase fetchAllCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should be able to list all categories.")
    public void should_be_able_to_list_all_categories() {

        var category1 = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("Tecnologia")
                .build();

        var category2 = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("Administração")
                .build();

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        var result = fetchAllCategoryUseCase.execute();

        assertThat(result).hasSize(2);
        verify(categoryRepository, times(1)).findAll();
    }
}
