package com.ecommerce.product.services;

import com.ecommerce.product.dtos.CategoryDTO;
import com.ecommerce.product.dtos.CategoryResponse;
import com.ecommerce.product.models.Category;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService  {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<CategoryDTO> getAllCategories();

    Category getCategoryByName(CategoryDTO categoryDTO);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    void deleteCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> deleteCategories(List<CategoryDTO> categoriesToDelete);

    CategoryDTO updateCategoryById(CategoryDTO categoryDTO, Long categoryId);
}
