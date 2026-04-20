package com.example.demo.service;

import com.example.demo.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author getById(Long id);

    Author create(Author author);

    Author update(Long id, Author author);

    void delete(Long id);
}