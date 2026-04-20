package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Category> getAll() {
        return repo.findAll();
    }

    @Override
    public Category getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Category create(Category category) {
        return repo.save(category);
    }

    @Override
    public Category update(Long id, Category newCategory) {
        return repo.findById(id).map(category -> {
            category.setCategoryTitle(newCategory.getCategoryTitle());
            return repo.save(category);
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}