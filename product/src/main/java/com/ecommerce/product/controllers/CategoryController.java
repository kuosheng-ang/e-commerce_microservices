package com.ecommerce.product.controllers;

import com.ecommerce.product.config.AppConstants;
import com.ecommerce.product.dtos.CategoryConfigInfoDto;
import com.ecommerce.product.dtos.CategoryDTO;
import com.ecommerce.product.dtos.CategoryResponse;
import com.ecommerce.product.dtos.ProductConfigInfoDto;
import com.ecommerce.product.models.Category;
import com.ecommerce.product.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Tag(
        name = "CRUD REST APIs for Category within kuoshengclement ECommerce",
        description = "CRUD REST APIs in kuoshengclement ECommerce to CREATE, UPDATE, FETCH AND DELETE Category Details"
)
@Validated
@RestController
@RequestMapping(value="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryConfigInfoDto categoryConfigInfoDto;

    private static Logger categorylogger = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping("/public/categories/fetchCategories")   // API Endpoint - tested working using Postman
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);


        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/categories/create")     // API Endpoint - tested working using Postman
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        if (savedCategoryDTO.isCreated) {
            categorylogger.info("Category: " + categoryDTO.getCategoryName() + "created successfully");
            return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
        } else {
            categorylogger.error("Category with the name already " + categoryDTO.getCategoryName() + "exists !!! - Category creation not successful");
            return new ResponseEntity<>(savedCategoryDTO, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/admin/categories/remove/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
            try {
                CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
                return new ResponseEntity<>(deletedCategory, HttpStatus.OK);

                //return new ResponseEntity<>(status, HttpStatus.Ok);
                //return ResponseEntity.ok(status);

            } catch (ResponseStatusException e) {
                return new ResponseEntity<>(e.getReason(), e.getStatusCode());
            }

    }

    @DeleteMapping("/admin/categories/remove/categorylist")
    public ResponseEntity<?> deleteCategoryList (@RequestBody List<CategoryDTO> categoryDtos){
        try {
            List<CategoryDTO> deletedCategories =  categoryService.deleteCategories(categoryDtos);
            return new ResponseEntity<>(deletedCategories, HttpStatus.OK);

            //return new ResponseEntity<>(status, HttpStatus.Ok);
            //return ResponseEntity.ok(status);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }

    }

    /* @PutMapping("/admin/categories/update/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId){
            CategoryDTO savedCategoryDTO = categoryService.updateCategoryById(categoryDTO, categoryId);
            return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }*/

    @PutMapping("/admin/categories/update")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }

    @GetMapping("/public/categories/config/info")
    public ResponseEntity<CategoryConfigInfoDto> getConfigInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryConfigInfoDto);
    }

}
