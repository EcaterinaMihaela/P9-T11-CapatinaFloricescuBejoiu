package com.example.demo.service.impl;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repo;

    public BookServiceImpl(BookRepository repo) {
        this.repo = repo;
    }

    public List<Book> getAll() { return repo.findAll(); }

    public Book getById(Long id) { return repo.findById(id).orElse(null); }

    public Book create(Book b) { return repo.save(b); }

    public Book update(Long id, Book b) {
        return repo.findById(id).map(book -> {
            book.setBookTitle(b.getBookTitle());
            book.setBookDescription(b.getBookDescription());
            book.setISBN(b.getISBN());
            book.setAvailableStock(b.getAvailableStock());
            book.setStatus(b.getStatus());
            return repo.save(book);
        }).orElse(null);
    }

    public void delete(Long id) { repo.deleteById(id); }
}
