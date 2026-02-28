package br.com.jhonecmd.courses_api.modules.category.courses.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.courses_api.modules.category.courses.usecases.DeleteCourseUseCase;
import br.com.jhonecmd.courses_api.modules.category.courses.usecases.FetchAllCourseUseCase;
import br.com.jhonecmd.courses_api.modules.category.courses.usecases.GetByCourseUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private FetchAllCourseUseCase fetchAllCourseUseCase;

    @Autowired
    private GetByCourseUseCase getByCourseUseCase;

    @Autowired
    private DeleteCourseUseCase deleteCourseUseCase;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    public ResponseEntity<Object> getByCourse(@PathVariable() String id) {
        try {

            var result = this.getByCourseUseCase.execute(id);
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    public ResponseEntity<Object> delete(@PathVariable() String id) {
        try {

            this.deleteCourseUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
