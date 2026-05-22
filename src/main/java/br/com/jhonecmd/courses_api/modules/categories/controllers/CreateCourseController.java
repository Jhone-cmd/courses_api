package br.com.jhonecmd.courses_api.modules.categories.controllers;

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
import br.com.jhonecmd.courses_api.exceptions.ErrorMessageDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.dto.CreateCourseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.usecases.CreateCourseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@Tag(name = "Courses", description = "Routes intended for courses.")
public class CreateCourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;

    @PostMapping("/{id}/courses")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    @Operation(summary = "Create a course.", description = "This route is designed to create a course.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Course created successfully"),

            @ApiResponse(responseCode = "409", description = "Course already exists.", content = {
                    @Content(schema = @Schema(implementation = CourseAlreadyExists.class, example = "Course already exists!"))
            }),

            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class))))
    })
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
