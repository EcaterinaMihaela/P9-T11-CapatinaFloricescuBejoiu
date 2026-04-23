package com.example.demo.service.impl;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.model.Author;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final RepositoryWrapper repo;

    public AuthorServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Author> getAll() {
        return repo.author.findAll();
    }

    @Override
    public Author getById(Long id) {
        return repo.author.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Override
    public Author create(AuthorDTO dto) {

        Author a = new Author();
        a.setAuthorName(dto.getAuthorName());

        return repo.author.save(a);
    }

    @Override
    public Author update(Long id, AuthorDTO dto) {

        return repo.author.findById(id).map(a -> {
            a.setAuthorName(dto.getAuthorName());
            return repo.author.save(a);
        }).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Override
    public void delete(Long id) {
        repo.author.deleteById(id);
    }
}