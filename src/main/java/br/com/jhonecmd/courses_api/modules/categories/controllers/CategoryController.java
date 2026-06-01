package br.com.jhonecmd.courses_api.modules.categories.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.courses_api.exceptions.CategoryAlreadyExists;
import br.com.jhonecmd.courses_api.exceptions.ErrorMessageDTO;
import br.com.jhonecmd.courses_api.modules.categories.dto.CategoryResponseDTO;
import br.com.jhonecmd.courses_api.modules.categories.dto.CreateCategoryDTO;
import br.com.jhonecmd.courses_api.modules.categories.dto.UpdateCategoryDTO;
import br.com.jhonecmd.courses_api.modules.categories.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.categories.usecases.CreateCategoryUseCase;
import br.com.jhonecmd.courses_api.modules.categories.usecases.DeleteCategoryUseCase;
import br.com.jhonecmd.courses_api.modules.categories.usecases.FetchAllCategoryUseCase;
import br.com.jhonecmd.courses_api.modules.categories.usecases.UpdateCategoryUseCase;
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
@Tag(name = "Categories", description = "Routes intended for categories.")
public class CategoryController {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private FetchAllCategoryUseCase fetchAllCategoryUseCase;

    @Autowired
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Autowired
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR')")
    @Operation(summary = "Create a category.", description = "This route is designed to create a category.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully"),

            @ApiResponse(responseCode = "409", description = "Category already exists.", content = {
                    @Content(schema = @Schema(implementation = CategoryAlreadyExists.class, example = "Category already exists!"))
            }),

            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class))))
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreateCategoryDTO createCategoryDTO) {
        try {

            var categoryEntity = CategoryEntity.builder().name(createCategoryDTO.getName()).build();
            this.createCategoryUseCase.execute(categoryEntity);

            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (CategoryAlreadyExists ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR') or hasRole('COORDINATOR')")
    @Operation(summary = "View categories.", description = "This route is designed to view categories.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of categories retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDTO.class))))
    })
    public ResponseEntity<Object> fetchAllCategories() {
        try {

            var result = this.fetchAllCategoryUseCase.execute();

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR')")
    @Operation(summary = "Update data a specific category.", description = "This route is designed to update data a specific category.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Data updated successfully", content = @Content(schema = @Schema(implementation = UpdateCategoryDTO.class)))
    })
    public ResponseEntity<Object> update(@PathVariable() String id,
            @RequestBody UpdateCategoryDTO updateCategoryDTO) {
        try {

            this.updateCategoryUseCase.execute(id, updateCategoryDTO);
            return ResponseEntity.ok(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR')")
    @Operation(summary = "Delete a specific category.", description = "This route is designed to delete a specific category.")
    @SecurityRequirement(name = "auth")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Category successfully deleted.")
    })
    public ResponseEntity<Object> delete(@PathVariable() String id) {
        try {

            this.deleteCategoryUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
