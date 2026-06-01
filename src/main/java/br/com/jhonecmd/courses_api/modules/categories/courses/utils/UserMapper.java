package br.com.jhonecmd.courses_api.modules.categories.courses.utils;

import br.com.jhonecmd.courses_api.modules.categories.courses.dto.UpdateCourseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.users.dto.UpdateUserDTO;
import br.com.jhonecmd.courses_api.modules.users.entities.UserEntity;

public interface UserMapper extends GenericMapper<UpdateUserDTO, UserEntity> {

}
