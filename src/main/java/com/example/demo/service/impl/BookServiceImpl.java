package com.example.demo.service.impl;

import com.example.demo.dto.BookDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Category;
import com.example.demo.model.Publisher;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PublisherRepository;
import com.example.demo.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           CategoryRepository categoryRepository,
                           PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book create(BookDTO dto) {

        Author author = authorRepository.findById(dto.getAuthorID()).orElse(null);
        Category category = categoryRepository.findById(dto.getCategoryID()).orElse(null);
        Publisher publisher = publisherRepository.findById(dto.getPublisherID()).orElse(null);

        Book book = new Book();
        book.setBookTitle(dto.getBookTitle());
        book.setBookDescription(dto.getBookDescription());
        book.setISBN(dto.getIsbn());
        book.setAvailableStock(dto.getAvailableStock());
        book.setStatus(dto.getStatus());

        book.setAuthor(author);
        book.setCategory(category);
        book.setPublisher(publisher);

        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, BookDTO dto) {
        return bookRepository.findById(id).map(book -> {

            book.setBookTitle(dto.getBookTitle());
            book.setBookDescription(dto.getBookDescription());
            book.setISBN(dto.getIsbn());
            book.setAvailableStock(dto.getAvailableStock());
            book.setStatus(dto.getStatus());

            Author author = authorRepository.findById(dto.getAuthorID()).orElse(null);
            Category category = categoryRepository.findById(dto.getCategoryID()).orElse(null);
            Publisher publisher = publisherRepository.findById(dto.getPublisherID()).orElse(null);

            book.setAuthor(author);
            book.setCategory(category);
            book.setPublisher(publisher);

            return bookRepository.save(book);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}