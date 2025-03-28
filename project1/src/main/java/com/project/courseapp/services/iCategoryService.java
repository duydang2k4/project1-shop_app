package com.project.courseapp.services;

import com.project.courseapp.dtos.CategoryDTO;
import com.project.courseapp.models.Category;

import java.util.List;

public interface iCategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long id, CategoryDTO category);
    void deleteCategory(long id);
}
