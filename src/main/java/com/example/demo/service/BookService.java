package com.example.demo.service;
import com.example.demo.model.Book;
import java.util.List;

public interface BookService {
    List<Book> getAll();
    Book getById(Long id);
    Book create(Book book);
    Book update(Long id, Book book);
    void delete(Long id);
}
