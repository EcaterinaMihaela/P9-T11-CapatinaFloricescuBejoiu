package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book getById(Long id);

    Book create(BookDTO dto);

    Book update(Long id, BookDTO dto);

    void delete(Long id);
}