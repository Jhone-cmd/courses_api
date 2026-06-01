package br.com.jhonecmd.courses_api.utils;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.com.jhonecmd.courses_api.modules.users.dto.UpdateUserDTO;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateUserDTO updateUserDTO, @MappingTarget UserEntity userEntity);
}
