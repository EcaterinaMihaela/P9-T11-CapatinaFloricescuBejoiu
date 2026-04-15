package com.example.demo.service.impl;
import com.example.demo.model.Librarian;
import com.example.demo.repository.LibrarianRepository;
import com.example.demo.service.LibrarianService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianServiceImpl implements LibrarianService {

    private final LibrarianRepository repo;

    public LibrarianServiceImpl(LibrarianRepository repo) {
        this.repo = repo;
    }

    public List<Librarian> getAll() { return repo.findAll(); }

    public Librarian getById(Long id) { return repo.findById(id).orElse(null); }

    public Librarian create(Librarian l) { return repo.save(l); }

    public Librarian update(Long id, Librarian l) {
        return repo.findById(id).map(x -> {
            x.setResponsibilities(l.getResponsibilities());
            return repo.save(x);
        }).orElse(null);
    }

    public void delete(Long id) { repo.deleteById(id); }
}
