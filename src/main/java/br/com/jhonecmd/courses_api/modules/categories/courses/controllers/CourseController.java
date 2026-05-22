package br.com.jhonecmd.courses_api.modules.categories.courses.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.courses_api.modules.categories.courses.dto.ChangeStatusCourseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.dto.CourseResponseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.dto.UpdateCourseDTO;
import br.com.jhonecmd.courses_api.modules.categories.courses.usecases.ChangeStatusCourseUseCase;
import br.com.jhonecmd.courses_api.modules.categories.courses.usecases.DeleteCourseUseCase;
import br.com.jhonecmd.courses_api.modules.categories.courses.usecases.FetchAllCourseUseCase;
import br.com.jhonecmd.courses_api.modules.categories.courses.usecases.GetByCourseUseCase;
import br.com.jhonecmd.courses_api.modules.categories.courses.usecases.UpdateCourseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/courses")
@Tag(name = "Courses", description = "Routes intended for courses.")
public class CourseController {

    @Autowired
    private FetchAllCourseUseCase fetchAllCourseUseCase;

    @Autowired
    private GetByCourseUseCase getByCourseUseCase;

    @Autowired
    private UpdateCourseUseCase updateCourseUseCase;

    @Autowired
    private ChangeStatusCourseUseCase changeStatusCourseUseCase;

    @Autowired
    private DeleteCourseUseCase deleteCourseUseCase;

    @GetMapping("")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    @Operation(summary = "View courses.", description = "This route is designed to view courses.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of courses retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourseResponseDTO.class))))
    })
    public ResponseEntity<Object> fetchAllCourses(@RequestParam(required = false) Boolean status) {
        try {

            var result = this.fetchAllCourseUseCase.execute(status);
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    @Operation(summary = "View data for a specific course.", description = "This route is designed to view data for a specific course..")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course data viewed.", content = @Content(schema = @Schema(implementation = CourseResponseDTO.class)))
    })
    public ResponseEntity<Object> getByCourse(@PathVariable() String id) {
        try {

            var result = this.getByCourseUseCase.execute(id);
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    @Operation(summary = "Update data a specific course.", description = "This route is designed to update data a specific course.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Data updated successfully", content = @Content(schema = @Schema(implementation = UpdateCourseDTO.class)))
    })
    public ResponseEntity<Object> updatedCourse(@PathVariable() String id,
            @RequestBody UpdateCourseDTO updateCourseDTO) {
        try {

            var course = this.updateCourseUseCase.execute(id, updateCourseDTO);
            return ResponseEntity.ok(course);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/{id}/active")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    @Operation(summary = "Update status a specific course.", description = "This route is designed to update status a specific course.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated successfully")
    })
    public ResponseEntity<Object> changeActive(@PathVariable() String id,
            @RequestBody ChangeStatusCourseDTO changeStatusCourseDTO) {
        try {

            this.changeStatusCourseUseCase.execute(id, changeStatusCourseDTO.getActive());
            return ResponseEntity.ok(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    @Operation(summary = "Delete a specific course.", description = "This route is designed to delete a specific course.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course successfully deleted.")
    })
    public ResponseEntity<Object> delete(@PathVariable() String id) {
        try {

            this.deleteCourseUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
