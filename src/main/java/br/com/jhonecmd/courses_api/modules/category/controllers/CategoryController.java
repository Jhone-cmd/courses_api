package br.com.jhonecmd.courses_api.modules.category.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.courses_api.modules.category.courses.dto.CreateCourseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.entities.CourseEntity;
import br.com.jhonecmd.courses_api.modules.category.courses.usecases.CreateCourseUseCase;
import br.com.jhonecmd.courses_api.modules.category.dto.CreateCategoryDTO;
import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.category.usecases.CreateCategoryUseCase;
import br.com.jhonecmd.courses_api.modules.category.usecases.FetchAllCategoryUseCase;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private FetchAllCategoryUseCase fetchAllCategoryUseCase;

    @Autowired
    private CreateCourseUseCase createCourseUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR')")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateCategoryDTO createCategoryDTO) {
        try {

            var categoryEntity = CategoryEntity.builder().name(createCategoryDTO.getName()).build();
            this.createCategoryUseCase.execute(categoryEntity);

            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/{id}/courses")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    public ResponseEntity<Object> addCourse(@PathVariable() String id,
            @Valid @RequestBody CreateCourseDTO createCourseDTO) {
        try {

            var courseEntity = CourseEntity.builder().name(createCourseDTO.getName())
                    .description(createCourseDTO.getDescription()).active(false).categoryId(UUID.fromString(id))
                    .build();
            this.createCourseUseCase.execute(courseEntity);

            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    public ResponseEntity<Object> fetchAllCategories() {
        try {

            var result = this.fetchAllCategoryUseCase.execute();

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
