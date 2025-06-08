package com.ecommerce.product.services.Impl;

import com.ecommerce.product.dtos.CategoryResponse;
import com.ecommerce.product.exceptions.APIException;
import com.ecommerce.product.exceptions.ResourceNotFoundException;
import com.ecommerce.product.models.Category;
import com.ecommerce.product.dtos.CategoryDTO;
import com.ecommerce.product.repositories.CategoryRepository;
import com.ecommerce.product.services.CategoryService;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty())
            throw new APIException("No category created till now.");

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse(categoryDTOS);
        categoryResponse.setCategoryDTOList(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }


    public List<CategoryDTO> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No category is created");
        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        return categoryDTOS;
    }


    public Category getCategoryByName(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDb == null) {
            throw new ResourceNotFoundException("Category","categoryName",category.getCategoryName());
        }
        return categoryFromDb;

    }


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());

        if (categoryFromDb != null) {
            categoryDTO.setCreated(false);
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists !!!");
        }
        categoryDTO.setCreated(true);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }


    public List<CategoryDTO> deleteCategories(List<CategoryDTO> categoriesToDelete) {

        return categoriesToDelete.stream().
                                map(c -> {
                                    Category category = modelMapper.map( c, Category.class);
                                    category.setDeleted(true);
                                   // categoryRepository.delete(category);
                                    return c;
                                }).collect(Collectors.toList());


    }

    public void deleteCategory(CategoryDTO categoryDTO) {


        //List<Category> categories = categoryRepository.findAll();
        //Category categoryDel = categories.stream().filter(c -> c.getCategoryName().equalsIgnoreCase(categoryName))
        //                    .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.SC_NOT_FOUND)));
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryDel = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryDel == null) {
            throw new ResourceNotFoundException("Category","categoryName",category.getCategoryName());
        }

        categoryRepository.delete(categoryDel);
        categoryDTO.setDeleted(true);

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {

        //List<Category> categories = categoryRepository.findAll();
        //Category savedCategory = categories.stream().filter(c -> c.getCategoryName().equalsIgnoreCase(categoryName))
        //        .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.SC_NOT_FOUND)));
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category updateCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if (updateCategory==null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.SC_NOT_FOUND));
        }

        updateCategory.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(updateCategory);
        return modelMapper.map(updateCategory, CategoryDTO.class);

    }


    public CategoryDTO updateCategoryById(CategoryDTO categoryDTO, Long categoryId) {

        //Optional<Category> categories = categoryRepository.findById(categoryId);
        List<Category> categories = categoryRepository.findAll();
        Category updateCategory = categories.stream().filter(c -> c.getCategoryId() == categoryId)
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.SC_NOT_FOUND)));
        //Optional<Category> updateCategory = Optional.ofNullable(categoryRepository.findById(categoryId).stream().findFirst()
        //        .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.SC_NOT_FOUND))));


        if (updateCategory != null) {
            //Category existingCategory = updateCategory.get();
            updateCategory.setCategoryName(categoryDTO.getCategoryName());
            categoryRepository.save(updateCategory);
            return modelMapper.map(updateCategory, CategoryDTO.class);

        }
        else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.SC_NOT_FOUND));
        }

        //Category savedCategory = categoryRepository.findById(categoryId)
        //        .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        /*Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryName(categoryDTO.getCategoryName());
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class); */
    }
}
