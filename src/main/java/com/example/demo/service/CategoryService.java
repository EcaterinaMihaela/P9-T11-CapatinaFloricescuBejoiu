package com.example.demo.service;

import com.example.demo.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getById(Long id);

    Category create(Category category);

    Category update(Long id, Category category);

    void delete(Long id);
}