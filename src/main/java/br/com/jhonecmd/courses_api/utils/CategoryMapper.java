package br.com.jhonecmd.courses_api.utils;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.com.jhonecmd.courses_api.modules.categories.dto.UpdateCategoryDTO;
import br.com.jhonecmd.courses_api.modules.categories.entities.CategoryEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateCategoryDTO updateCategoryDTO, @MappingTarget CategoryEntity categoryEntity);
}
