package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final RepositoryWrapper repo;

    public CategoryServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Category> getAll() {
        return repo.category.findAll();
    }

    @Override
    public Category getById(Long id) {
        return repo.category.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public Category create(Category category) {
        return repo.category.save(category);
    }

    @Override
    public Category update(Long id, Category newCategory) {

        Category category = getById(id);

        category.setCategoryTitle(newCategory.getCategoryTitle());

        return repo.category.save(category);
    }

    @Override
    public void delete(Long id) {
        repo.category.deleteById(id);
    }
}