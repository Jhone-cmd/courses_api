package br.com.jhonecmd.courses_api.modules.category.courses.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.courses_api.modules.category.courses.dto.CourseResponseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.repositories.CourseRepository;

@Service
public class FetchAllCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseResponseDTO> execute(Boolean status) {

        var courses = this.courseRepository.findAll();

        return courses.stream().filter((course) -> status == null || course.getActive().equals(status))
                .map((course) -> CourseResponseDTO.builder().id(course.getId()).name(course.getName())
                        .description(course.getDescription()).categoryName(course.getCategoryEntity().getName())
                        .active(course.getActive()).build())
                .toList();
    }
}
