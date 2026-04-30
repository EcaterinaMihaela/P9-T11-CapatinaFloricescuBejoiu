package com.example.demo.service.impl;

import com.example.demo.dto.BookDTO;
import com.example.demo.model.*;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final RepositoryWrapper repo;

    public BookServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Book> getAll() {
        return repo.book.findAll();
    }

    @Override
    public Book getById(Long id) {
        return repo.book.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public Book create(BookDTO dto) {

        Book book = mapDtoToEntity(new Book(), dto);
        return repo.book.save(book);
    }

    @Override
    public Book update(Long id, BookDTO dto) {

        Book book = getById(id);

        mapDtoToEntity(book, dto);

        return repo.book.save(book);
    }

    @Override
    public void delete(Long id) {
        repo.book.deleteById(id);
    }

    // helper method (clean code)
    private Book mapDtoToEntity(Book book, BookDTO dto) {

        book.setBookTitle(dto.getBookTitle());
        book.setBookDescription(dto.getBookDescription());
        book.setISBN(dto.getIsbn());
        book.setAvailableStock(dto.getAvailableStock());
        book.setStatus(dto.getStatus());
        book.setImageUrl(dto.getImageUrl());

        Author author = repo.author.findById(dto.getAuthorID())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Category category = repo.category.findById(dto.getCategoryID())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Publisher publisher = repo.publisher.findById(dto.getPublisherID())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        book.setAuthor(author);
        book.setCategory(category);
        book.setPublisher(publisher);

        return book;
    }
}