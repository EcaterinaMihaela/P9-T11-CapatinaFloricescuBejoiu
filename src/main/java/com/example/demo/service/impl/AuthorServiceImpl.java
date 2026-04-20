package com.example.demo.service.impl;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repo;

    public AuthorServiceImpl(AuthorRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Author> getAll() {
        return repo.findAll();
    }

    @Override
    public Author getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Author create(Author author) {
        return repo.save(author);
    }

    @Override
    public Author update(Long id, Author newAuthor) {
        return repo.findById(id).map(author -> {
            author.setAuthorName(newAuthor.getAuthorName());
            return repo.save(author);
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}