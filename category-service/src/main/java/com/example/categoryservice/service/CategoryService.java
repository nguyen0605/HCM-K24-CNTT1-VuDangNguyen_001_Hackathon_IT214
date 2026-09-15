package com.example.categoryservice.service;

import com.example.categoryservice.dto.CategoryResponse;
import com.example.categoryservice.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(Category category);
}
