package br.com.jhonecmd.courses_api.modules.category.courses.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.courses_api.modules.category.courses.usecases.FetchAllCourseUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private FetchAllCourseUseCase fetchAllCourseUseCase;

    @GetMapping("")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    public ResponseEntity<Object> fetchAllCourses() {
        try {

            var result = this.fetchAllCourseUseCase.execute();
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
