package br.com.jhonecmd.courses_api.modules.category.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.courses_api.exceptions.CourseAlreadyExists;
import br.com.jhonecmd.courses_api.modules.category.courses.dto.CreateCourseDTO;
import br.com.jhonecmd.courses_api.modules.category.courses.usecases.CreateCourseUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CreateCourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;

    @PostMapping("/{id}/courses")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    public ResponseEntity<Object> addCourse(@PathVariable() String id,
            @Valid @RequestBody CreateCourseDTO createCourseDTO) {
        try {

            this.createCourseUseCase.execute(id, createCourseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (CourseAlreadyExists ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
