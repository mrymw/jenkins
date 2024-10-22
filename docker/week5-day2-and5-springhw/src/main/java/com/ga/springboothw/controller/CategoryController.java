package com.ga.springboothw.controller;

import com.ga.springboothw.model.Category;
import com.ga.springboothw.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired // creates class on its own
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //CRUD Operations
    //CRUD - C - HTTP POST - To create a record
    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category categoryObject) {
        System.out.println("Calliing create category");
        return categoryService.createCategory(categoryObject);
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        System.out.println("Calling getCategories");
        return categoryService.getCategories();
    }

    @GetMapping("/categories/{categoryID}")
    public Optional<Category> getCategory(@PathVariable Long categoryID) {
        System.out.println("Calling getCategory");
        return categoryService.getCategory(categoryID);
    }

    @DeleteMapping("/categories/delete/{categoryID}")
    public Optional<Category> deleteCategory(@PathVariable Long categoryID) throws Exception {
        System.out.println("Calling deleteCategory");
        return categoryService.deleteCategory(categoryID);
    }

    @PutMapping("/categories/update/{categoryID}")
    public Optional<Category> updateCategory(@PathVariable Long categoryID, @RequestBody Category categoryObject) throws Exception {
        System.out.println("Calling updateCategory");
        return categoryService.updateCategory(categoryID, categoryObject);
    }
}