package com.example.demo.service;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author getById(Long id);

    Author create(AuthorDTO dto);

    Author update(Long id, AuthorDTO dto);

    void delete(Long id);
}