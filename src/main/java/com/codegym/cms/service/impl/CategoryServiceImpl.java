package com.codegym.cms.service.impl;

import com.codegym.cms.model.Category;
import com.codegym.cms.repository.CategoryRepository;
import com.codegym.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }
    public Category findById(Long id){
        return categoryRepository.findOne(id);
    }
    public void remove(Long id){
        categoryRepository.delete(id);
    }

    public void save(Category category){
        categoryRepository.save(category);
    }
}
