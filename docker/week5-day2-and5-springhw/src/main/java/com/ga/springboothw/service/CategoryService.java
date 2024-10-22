package com.ga.springboothw.service;

import com.ga.springboothw.exception.InformationNotFoundException;
import com.ga.springboothw.model.Category;
import com.ga.springboothw.model.User;
import com.ga.springboothw.repository.CategoryRepository;
import com.ga.springboothw.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    @Autowired // creates class on its own
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository= categoryRepository;
    }
    public static User getCurrentLoggedUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
    public Category createCategory(Category categoryObject) {
        System.out.println("Service Calling create category");
        Category category = categoryRepository.findByName(categoryObject.getName());
        if (category != null) {
            throw new InformationNotFoundException("Category with name: " + category.getName() + " alrady exists");
        } else {
            categoryObject.setUser(getCurrentLoggedUser());
            return categoryRepository.save(categoryObject);
        }
    }
    public List<Category> getCategories() {
        System.out.println("Service Calling getCategrories: ");
        return categoryRepository.findByUserId(getCurrentLoggedUser().getId());
    }
    public Optional<Category>getCategory(Long categoryID) {
        System.out.println("Service Calling getCategory: ");
        Optional<Category> category = categoryRepository.findById(categoryID);
        if (category.isPresent()) {
            return category;
        } else {
            throw new InformationNotFoundException("Category with ID: " + categoryID + "not found");
        }
    }
    public Optional<Category> deleteCategory(Long categoryID) throws Exception {
        System.out.println("Service Calling deleteID: ");
        Optional<Category> categoryOptional = categoryRepository.findById(categoryID);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            User user = getCurrentLoggedUser();
            if (category.getUser().getId().equals(user.getId())) {
                categoryRepository.deleteById(categoryID);
                return categoryOptional;
            } else {
                throw new AccessDeniedException("You don't have the permission to delete this category");
            }
        } else {
            throw new InformationNotFoundException("Category with ID: " + categoryID + "not found");
        }
    }
    public Optional<Category> updateCategory (Long categoryID, Category updateCategory) throws Exception {
        System.out.println("Service calling updateID :");
        Optional<Category> categoryOptional = categoryRepository.findById(categoryID);
        if (categoryOptional.isPresent()) {
            Category update = categoryOptional.get();
            User user = getCurrentLoggedUser();
            if (update.getUser().getId().equals(user.getId())) {
                update.setName(updateCategory.getName());
                update.setDescription(updateCategory.getDescription());
                return Optional.of(categoryRepository.save(update));
            } else {
                throw new AccessDeniedException("You don't have the permission to delete this category");
            }
        } else {
            throw new InformationNotFoundException("Category with ID: " + categoryID + "not found");
        }
    }
}
