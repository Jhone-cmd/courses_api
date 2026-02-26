package br.com.jhonecmd.courses_api.modules.category.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.courses_api.modules.category.dto.CreateCategoryDTO;
import br.com.jhonecmd.courses_api.modules.category.entities.CategoryEntity;
import br.com.jhonecmd.courses_api.modules.category.usecases.CreateCategoryUseCase;
import br.com.jhonecmd.courses_api.modules.category.usecases.FetchAllCategoryUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private FetchAllCategoryUseCase fetchAllCategoryUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('RECTOR') or hasRole('DIRECTOR')")
    public ResponseEntity<Object> create(@RequestBody CreateCategoryDTO createCategoryDTO) {
        try {

            var categoryEntity = CategoryEntity.builder().name(createCategoryDTO.getName()).build();
            this.createCategoryUseCase.execute(categoryEntity);

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
